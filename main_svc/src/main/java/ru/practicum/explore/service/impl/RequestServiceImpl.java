package ru.practicum.explore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.practicum.explore.dto.ParticipationRequestDto;
import ru.practicum.explore.enums.EventStateEnum;
import ru.practicum.explore.enums.ParticipationRequestStateEnum;
import ru.practicum.explore.exceptions.ConflictException;
import ru.practicum.explore.exceptions.EntityDoesNotExistsException;
import ru.practicum.explore.mappers.RequestMapper;
import ru.practicum.explore.model.Event;
import ru.practicum.explore.model.ParticipationRequest;
import ru.practicum.explore.model.User;
import ru.practicum.explore.repository.EventRepository;
import ru.practicum.explore.repository.RequestRepository;
import ru.practicum.explore.repository.UserRepository;
import ru.practicum.explore.service.RequestService;

import java.util.List;

@Service
@Slf4j
@Primary
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        log.debug("Get participation request received in service {}", this.getClass());
        List<ParticipationRequest> requests = requestRepository.findAllByRequesterId(userId);
        List<ParticipationRequestDto> requestDtos = requestMapper.toDto(requests);
        log.debug("Get participation request processed in service {}", this.getClass());
        return requestDtos;
    }

    @Override
    public ParticipationRequestDto createParticipationRequest(Long userId, Long eventId) {
        log.debug("Create participation request received in service {}", this.getClass());
        ParticipationRequest request = new ParticipationRequest();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityDoesNotExistsException(User.class, userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityDoesNotExistsException(Event.class, eventId));
        request.setRequester(user).setEvent(event);
        validateParticipationRequest(request);
        requestRepository.save(request);
        ParticipationRequestDto requestDto = requestMapper.toDto(request);
        log.debug("Create participation request processed in service {}", this.getClass());
        return requestDto;
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        log.debug("Cancel participation request {} received in service {}", requestId, this.getClass());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityDoesNotExistsException(User.class, userId));
        ParticipationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityDoesNotExistsException(ParticipationRequest.class, userId));
        Event event = request.getEvent();
        if (!request.getRequester().getId().equals(userId)) {
            throw new ConflictException(ParticipationRequest.class);
        }
        request.setStatus(ParticipationRequestStateEnum.CANCELED);
        event.setConfirmedRequests(event.getConfirmedRequests() - 1);
        eventRepository.save(event);
        requestRepository.save(request);
        ParticipationRequestDto requestDto = requestMapper.toDto(request);
        log.debug("Cancel participation request {} processed in service {}", requestId, this.getClass());
        return requestDto;
    }

    private void validateParticipationRequest(ParticipationRequest request) {
        Long requesterId = request.getRequester().getId();
        Long initiatorId = request.getEvent().getInitiator().getId();
        Long eventId = request.getEvent().getId();
        Event event = request.getEvent();
        if (requestRepository.existsByRequester_IdAndEvent_Id(requesterId, eventId) ||
                requesterId.equals(initiatorId) ||
                !event.getState().equals(EventStateEnum.PUBLISHED) ||
                event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            throw new ConflictException(request.getClass());
        }
        if (!event.getRequestModeration() &&
                event.getParticipantLimit() > event.getConfirmedRequests()) {
            request.setStatus(ParticipationRequestStateEnum.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        } else {
            request.setStatus(ParticipationRequestStateEnum.PENDING);
        }
    }
}
