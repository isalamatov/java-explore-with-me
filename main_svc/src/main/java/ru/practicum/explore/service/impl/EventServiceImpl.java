package ru.practicum.explore.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.dto.EventFullDto;
import ru.practicum.explore.dto.EventShortDto;
import ru.practicum.explore.dto.NewEventDto;
import ru.practicum.explore.dto.ParticipationRequestDto;
import ru.practicum.explore.enums.EventStateEnum;
import ru.practicum.explore.enums.ParticipationRequestStateEnum;
import ru.practicum.explore.enums.SortEnum;
import ru.practicum.explore.enums.StateActionEnum;
import ru.practicum.explore.exceptions.ConflictException;
import ru.practicum.explore.exceptions.EntityDoesNotExistsException;
import ru.practicum.explore.exceptions.ForbiddenConditionsException;
import ru.practicum.explore.mappers.EventMapper;
import ru.practicum.explore.mappers.RequestMapper;
import ru.practicum.explore.model.*;
import ru.practicum.explore.repository.CategoryRepository;
import ru.practicum.explore.repository.EventRepository;
import ru.practicum.explore.repository.RequestRepository;
import ru.practicum.explore.repository.UserRepository;
import ru.practicum.explore.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Primary
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
@Import(FeignClientsConfiguration.class)
public class EventServiceImpl implements EventService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final EventMapper eventMapper;
    private final RequestMapper requestMapper;
    private static final Integer MAX_DELAY_FOR_PUBLISH = 1;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<EventShortDto> getEventsByUserId(Long userId, Integer from, Integer size) {
        log.debug("Get events by userId {} received in service {}", userId, this.getClass());
        Pageable page = PageRequest.of(from / size, size);
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityDoesNotExistsException(User.class, userId));
        List<Event> events = eventRepository.findEventsByInitiator(user, page);
        List<EventShortDto> eventShortDtos = eventMapper.toShortDto(events);
        log.debug("Get events {} by userId {} processed in service {}", eventShortDtos, userId, this.getClass());
        return eventShortDtos;
    }

    @Override
    public EventFullDto postEventByUserId(Long userId, NewEventDto newEventDto) {
        log.debug("Post event {} by userId {} received in service {}", newEventDto, userId, this.getClass());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityDoesNotExistsException(User.class, userId));
        Event event = eventMapper.fromNewEventDto(newEventDto);
        initializeEvent(event, user);
        Event createdEvent = eventRepository.save(event);
        EventFullDto eventFullDto = eventMapper.toFullDto(createdEvent);
        log.debug("Post event {} by userId {} processed in service {}", createdEvent, userId, this.getClass());
        return eventFullDto;
    }

    @Override
    public EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId) {
        log.debug("Get event {} by userId {} received in service {}", eventId, userId, this.getClass());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityDoesNotExistsException(User.class, userId));
        Event event = eventRepository.findByIdAndInitiator(eventId, user)
                .orElseThrow(() -> new EntityDoesNotExistsException(Event.class, userId));
        EventFullDto eventFullDto = eventMapper.toFullDto(event);
        log.debug("Get event {} by userId {} processed in service {}", event, userId, this.getClass());
        return eventFullDto;
    }

    @Override
    public EventFullDto updateEvent(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        log.debug("Update event {} by userId {} received in service {}", eventId, userId, this.getClass());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityDoesNotExistsException(User.class, userId));
        Event event = eventRepository.findByIdAndInitiator(eventId, user)
                .orElseThrow(() -> new EntityDoesNotExistsException(Event.class, eventId));
        if (event.getState().equals(EventStateEnum.PUBLISHED)) {
            throw new ForbiddenConditionsException("Only pending or canceled events can be changed");
        }
        updateFields(event, updateEventUserRequest);
        Event updatedEvent = eventRepository.save(event);
        EventFullDto eventFullDto = eventMapper.toFullDto(event);
        log.debug("Update event {} by userId {} processed in service {}", updatedEvent, userId, this.getClass());
        return eventFullDto;
    }

    @Override
    public List<ParticipationRequestDto> getParticipationRequests(Long userId, Long eventId) {
        log.debug("Get requests for event {} by userId {} received in service {}", eventId, userId, this.getClass());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityDoesNotExistsException(User.class, userId));
        Event event = eventRepository.findByIdAndInitiator(eventId, user)
                .orElseThrow(() -> new EntityDoesNotExistsException(Event.class, userId));
        if (!user.getId().equals(event.getInitiator().getId())) {
            throw new ConflictException(User.class);
        }
        List<ParticipationRequest> requests = event.getRequests();
        List<ParticipationRequestDto> participationRequestDto = requestMapper.toDto(requests);
        log.debug("Get requests for event {} by userId {} processed in service {}", eventId, userId, this.getClass());
        return participationRequestDto;
    }

    @Override
    public EventRequestStatusUpdateResult changeRequestState(Long userId, Long eventId, EventRequestStatusUpdateRequest request) {
        log.debug("Change request state for event {} by userId {} received in service {}", eventId, userId, this.getClass());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityDoesNotExistsException(User.class, userId));
        Event event = eventRepository.findByIdAndInitiator(eventId, user)
                .orElseThrow(() -> new EntityDoesNotExistsException(Event.class, userId));
        if (!user.getId().equals(event.getInitiator().getId())) {
            throw new ConflictException(User.class);
        }
        List<ParticipationRequest> requests = requestRepository.findAllByIdIn(request.getRequestIds());
        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        if (request.getStatus().equals(EventRequestStatusUpdateRequest.StatusEnum.CONFIRMED)) {
            for (ParticipationRequest participationRequest : requests) {
                if (event.getParticipantLimit() > event.getConfirmedRequests()) {
                    participationRequest.setStatus(ParticipationRequestStateEnum.CONFIRMED);
                    event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                    result.getConfirmedRequests().add(requestMapper.toDto(participationRequest));
                } else {
                    participationRequest.setStatus(ParticipationRequestStateEnum.CANCELED);
                    result.getRejectedRequests().add(requestMapper.toDto(participationRequest));
                    throw new ConflictException(ParticipationRequest.class);
                }
            }
        } else {
            for (ParticipationRequest participationRequest : requests) {
                if (!participationRequest.getStatus().equals(ParticipationRequestStateEnum.CONFIRMED)) {
                    participationRequest.setStatus(ParticipationRequestStateEnum.REJECTED);
                    result.getRejectedRequests().add(requestMapper.toDto(participationRequest));
                } else {
                    throw new ConflictException(ParticipationRequest.class);
                }
            }
        }
        eventRepository.save(event);
        log.debug("Change request state for event {} by userId {} processed in service {}", eventId, userId, this.getClass());
        return result;
    }

    @Override
    public List<EventFullDto> getEventsBySearchParams(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, Integer from, Integer size) {
        log.debug("Search events request received in service {}", this.getClass());
        List<BooleanExpression> conditions = new ArrayList<>();
        if (users != null) {
            conditions.add(QEvent.event.initiator.id.in(users));
        }
        if (states != null) {
            List<EventStateEnum> stateEnums = states.stream().map(EventStateEnum::valueOf).collect(Collectors.toList());
            conditions.add(QEvent.event.state.in(stateEnums));
        }
        if (categories != null) {
            List<Category> categoryList = categoryRepository.findByIdIn(categories);
            conditions.add(QEvent.event.category.in(categoryList));
        }
        if (!rangeStart.isBlank()) {
            LocalDateTime startTime = LocalDateTime.parse(rangeStart, formatter);
            conditions.add(QEvent.event.eventDate.after(startTime));
        }
        if (!rangeEnd.isBlank()) {
            LocalDateTime endTime = LocalDateTime.parse(rangeEnd, formatter);
            conditions.add(QEvent.event.eventDate.before(endTime));
        }
        BooleanExpression finalCondition = conditions.stream().reduce(BooleanExpression::and).get();
        Pageable page = PageRequest.of(from / size, size);
        Iterable<Event> events = eventRepository.findAll(finalCondition, page);
        List<EventFullDto> eventFullDtos = eventMapper.toFullDto(events);
        log.debug("Search events request processed successfully in controller {}", this.getClass());
        return eventFullDtos;
    }

    @Override
    public EventFullDto updateEventByAdminRequest(Long eventId, UpdateEventAdminRequest request) {
        log.debug("Update event {} by admin received in service {}", eventId, this.getClass());
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityDoesNotExistsException(Event.class, eventId));
        updateFields(event, request);
        Event updatedEvent = eventRepository.save(event);
        EventFullDto eventFullDto = eventMapper.toFullDto(event);
        log.debug("Update event {} by admin processed in service {}", updatedEvent, this.getClass());
        return eventFullDto;
    }

    @Override
    public List<EventShortDto> getEventsByUserBySearchParams(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, SortEnum sort, Integer from, Integer size, HttpServletRequest request) {
        log.debug("Search events request received in service {}", this.getClass());
        List<BooleanExpression> conditions = new ArrayList<>();
        if (text != null && !text.isBlank()) {
            conditions.add(QEvent.event.annotation.likeIgnoreCase(text)
                    .or(QEvent.event.description.likeIgnoreCase(text)));
        }
        if (categories != null) {
            List<Category> categoryList = categoryRepository.findByIdIn(categories);
            conditions.add(QEvent.event.category.in(categoryList));
        }
        if (paid != null) {
            conditions.add(QEvent.event.paid.eq(paid));
        }
        if (!rangeStart.isBlank()) {
            LocalDateTime startTime = LocalDateTime.parse(rangeStart, formatter);
            conditions.add(QEvent.event.eventDate.after(startTime));
        }
        if (!rangeEnd.isBlank()) {
            LocalDateTime endTime = LocalDateTime.parse(rangeEnd, formatter);
            conditions.add(QEvent.event.eventDate.before(endTime));
        }
        BooleanExpression finalCondition = conditions.stream().reduce(BooleanExpression::and).get();
        Comparator<Event> comparator = Comparator.comparing(Event::getId);
        if (sort.equals(SortEnum.EVENT_DATE)) {
            comparator = Comparator.comparing(Event::getEventDate);
        } else if (sort.equals(SortEnum.VIEWS)) {
            comparator = Comparator.comparing(Event::getViews);
        } else if (sort.equals(SortEnum.RATING)) {
            comparator = Comparator.comparing(Event::getRating).reversed();
        }
        Pageable page = PageRequest.of(from / size, size);
        List<Event> events = eventRepository.findAll(finalCondition, page).stream()
                .filter(x -> x.getParticipantLimit() > x.getConfirmedRequests())
                .map(x -> x.setViews(x.getViews() + 1))
                .sorted(comparator)
                .collect(Collectors.toList());
        List<EventShortDto> eventShortDtos = eventMapper.toShortDto(events);
        log.debug("Search events request processed successfully in service {}", this.getClass());
        return eventShortDtos;
    }

    @Override
    public EventFullDto getEventById(Long eventId, HttpServletRequest request) {
        log.debug("Get event {} request received in controller {}", eventId, this.getClass());
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityDoesNotExistsException(Event.class, eventId));
        EventFullDto eventFullDto = eventMapper.toFullDto(event);
        event.setViews(event.getViews() + 1);
        log.debug("Get event {} request processed in controller {}", eventId, this.getClass());
        return eventFullDto;
    }

    private void initializeEvent(Event event, User user) {
        event.setInitiator(user);
        event.setState(EventStateEnum.PENDING);
        event.setCreatedOn(LocalDateTime.now());
    }

    private void updateFields(Event event, UpdateEventUserRequest request) {
        if (request.getAnnotation() != null) {
            event.setAnnotation(request.getAnnotation());
        }
        if (request.getCategory() != null) {
            Category category = categoryRepository.findById(request.getCategory())
                    .orElseThrow(() -> new EntityDoesNotExistsException(Category.class, request.getCategory()));
            event.setCategory(category);
        }
        if (request.getDescription() != null) {
            event.setDescription(request.getDescription());
        }
        if (request.getEventDate() != null) {
            event.setEventDate(request.getEventDate());
        }
        if (request.getLocation() != null) {
            event.setLocation(request.getLocation());
        }
        if (request.getParticipantLimit() != null) {
            event.setParticipantLimit(request.getParticipantLimit());
        }
        if (request.getRequestModeration() != null) {
            event.setRequestModeration(request.getRequestModeration());
        }
        if (request.getStateAction() == StateActionEnum.SEND_TO_REVIEW) {
            event.setState(EventStateEnum.PENDING);
        }
        if (request.getStateAction() == StateActionEnum.CANCEL_REVIEW) {
            event.setState(EventStateEnum.CANCELED);
        }
        if (request.getStateAction() == StateActionEnum.REJECT_EVENT) {
            if (event.getState().equals(EventStateEnum.PUBLISHED)) {
                throw new ConflictException(StateActionEnum.class);
            }
            event.setState(EventStateEnum.CANCELED);
        }
        if (request.getStateAction() == StateActionEnum.PUBLISH_EVENT) {
            if (event.getEventDate().plusHours(MAX_DELAY_FOR_PUBLISH).isBefore(LocalDateTime.now())
                    || !event.getState().equals(EventStateEnum.PENDING)) {
                throw new ConflictException(StateActionEnum.class);
            }
            event.setState(EventStateEnum.PUBLISHED);
        }
        if (request.getTitle() != null) {
            event.setTitle(request.getTitle());
        }
        if (request.getPaid() != null) {
            event.setPaid(request.getPaid());
        }
    }
}
