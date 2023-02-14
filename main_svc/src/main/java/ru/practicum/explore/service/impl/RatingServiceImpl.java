package ru.practicum.explore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.practicum.explore.enums.EventStateEnum;
import ru.practicum.explore.exceptions.ConflictException;
import ru.practicum.explore.exceptions.EntityDoesNotExistsException;
import ru.practicum.explore.exceptions.ForbiddenConditionsException;
import ru.practicum.explore.model.Event;
import ru.practicum.explore.model.User;
import ru.practicum.explore.repository.EventRepository;
import ru.practicum.explore.repository.UserRepository;
import ru.practicum.explore.service.RatingService;

@Service
@Slf4j
@Primary
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public void addLike(long eventId, long userId) {
        log.debug("Add like request to event {} from user {} received in service {}", eventId, userId, this.getClass());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityDoesNotExistsException(User.class, userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityDoesNotExistsException(Event.class, eventId));
        if (!event.getState().equals(EventStateEnum.PUBLISHED)) {
            throw  new ForbiddenConditionsException("Adding like is possible only for published events");
        }
        if (event.getInitiator().equals(user) || event.getLikedBy().contains(user)) {
            throw new ConflictException(User.class);
        }
        if (event.getDislikedBy().contains(user)) {
            deleteDislike(eventId, userId);
            event.decrementRating();
        }
        event.getLikedBy().add(user);
        user.getLikedEvents().add(event);
        event.incrementRating();
        eventRepository.save(event);
        userRepository.save(user);
        log.debug("Add like request to event {} from user {} processed in service {}", eventId, userId, this.getClass());
    }

    @Override
    public void addDislike(long eventId, long userId) {
        log.debug("Add dislike request to event {} from user {} received in service {}", eventId, userId, this.getClass());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityDoesNotExistsException(User.class, userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityDoesNotExistsException(Event.class, eventId));
        if (!event.getState().equals(EventStateEnum.PUBLISHED)) {
            throw  new ForbiddenConditionsException("Adding dislike is possible only for published events");
        }
        if (event.getInitiator().equals(user) || event.getDislikedBy().contains(user)) {
            throw new ConflictException(User.class);
        }
        if (event.getLikedBy().contains(user)) {
            deleteLike(eventId, userId);
            event.incrementRating();
        }
        event.getDislikedBy().add(user);
        user.getDislikedEvents().add(event);
        event.decrementRating();
        eventRepository.save(event);
        userRepository.save(user);
        log.debug("Add dislike request to event {} from user {} processed in service {}", eventId, userId, this.getClass());
    }

    @Override
    public void deleteLike(long eventId, long userId) {
        log.debug("Delete like request to event {} from user {} received in service {}", eventId, userId, this.getClass());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityDoesNotExistsException(User.class, userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityDoesNotExistsException(Event.class, eventId));
        if (event.getLikedBy().contains(user) && user.getLikedEvents().contains(event)) {
            event.getLikedBy().remove(user);
            user.getLikedEvents().remove(event);
            event.decrementRating();
        } else {
            throw new ConflictException(this.getClass());
        }
        eventRepository.save(event);
        userRepository.save(user);
        log.debug("Delete like request to event {} from user {} processed in service {}", eventId, userId, this.getClass());
    }

    @Override
    public void deleteDislike(long eventId, long userId) {
        log.debug("Delete dislike request to event {} from user {} received in service {}", eventId, userId, this.getClass());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityDoesNotExistsException(User.class, userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityDoesNotExistsException(Event.class, eventId));
        if (event.getDislikedBy().contains(user) && user.getDislikedEvents().contains(event)) {
            event.getDislikedBy().remove(user);
            user.getDislikedEvents().remove(event);
            event.incrementRating();
        } else {
            throw new ConflictException(this.getClass());
        }
        eventRepository.save(event);
        userRepository.save(user);
        log.debug("Delete dislike request to event {} from user {} processed in service {}", eventId, userId, this.getClass());
    }
}
