package ru.practicum.explore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.dto.EventFullDto;
import ru.practicum.explore.dto.EventShortDto;
import ru.practicum.explore.dto.NewEventDto;
import ru.practicum.explore.dto.ParticipationRequestDto;
import ru.practicum.explore.model.EventRequestStatusUpdateRequest;
import ru.practicum.explore.model.EventRequestStatusUpdateResult;
import ru.practicum.explore.model.UpdateEventUserRequest;
import ru.practicum.explore.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@Slf4j
@RequiredArgsConstructor
public class PrivateEventController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getEventsByUserId(@PathVariable Long userId,
                                                 @RequestParam(required = false, defaultValue = "0")
                                                 @PositiveOrZero Integer from,
                                                 @RequestParam(required = false, defaultValue = "25")
                                                 @Positive Integer size) {
        log.debug("Get events by userId {} received in controller {}", userId, this.getClass());
        List<EventShortDto> eventShortDtos = eventService.getEventsByUserId(userId, from, size);
        log.debug("Get events {} by userId {} processed in controller {}", eventShortDtos, userId, this.getClass());
        return eventShortDtos;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto postEventByUserId(@PathVariable Long userId,
                                          @Valid @RequestBody NewEventDto newEventDto) {
        log.debug("Post event {} by userId {} received in controller {}", newEventDto, userId, this.getClass());
        EventFullDto createdEvent = eventService.postEventByUserId(userId, newEventDto);
        log.debug("Post event {} by userId {} processed in controller {}", createdEvent, userId, this.getClass());
        return createdEvent;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventByUserIdAndEventId(@PathVariable Long userId,
                                                   @PathVariable Long eventId) {
        log.debug("Get event {} by userId {} received in controller {}", eventId, userId, this.getClass());
        EventFullDto event = eventService.getEventByUserIdAndEventId(userId, eventId);
        log.debug("Get event {} by userId {} processed in controller {}", event, userId, this.getClass());
        return event;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable Long userId,
                                    @PathVariable Long eventId,
                                    @RequestBody @Valid UpdateEventUserRequest updateEventUserRequest) {
        log.debug("Update event {} by userId {} received in controller {}", eventId, userId, this.getClass());
        EventFullDto updatedEvent = eventService.updateEvent(userId, eventId, updateEventUserRequest);
        log.debug("Update event {} by userId {} processed in controller {}", updatedEvent, userId, this.getClass());
        return updatedEvent;
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getParticipationRequests(@PathVariable Long userId,
                                                            @PathVariable Long eventId) {
        log.debug("Get requests for event {} by userId {} received in controller {}", eventId, userId, this.getClass());
        List<ParticipationRequestDto> participationRequestDto = eventService.getParticipationRequests(userId, eventId);
        log.debug("Get requests for event {} by userId {} processed in controller {}", eventId, userId, this.getClass());
        return participationRequestDto;
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult changeRequestState(@PathVariable Long userId,
                                                             @PathVariable Long eventId,
                                                             @Valid
                                                             @RequestBody EventRequestStatusUpdateRequest request) {
        log.debug("Change request state for event {} by userId {} received in controller {}", eventId, userId, this.getClass());
        EventRequestStatusUpdateResult result = eventService.changeRequestState(userId, eventId, request);
        log.debug("Change request state for event {} by userId {} processed in controller {}", eventId, userId, this.getClass());
        return result;
    }
}
