package ru.practicum.explore.service;

import ru.practicum.explore.dto.EventFullDto;
import ru.practicum.explore.dto.EventShortDto;
import ru.practicum.explore.dto.NewEventDto;
import ru.practicum.explore.dto.ParticipationRequestDto;
import ru.practicum.explore.enums.SortEnum;
import ru.practicum.explore.model.EventRequestStatusUpdateRequest;
import ru.practicum.explore.model.EventRequestStatusUpdateResult;
import ru.practicum.explore.model.UpdateEventAdminRequest;
import ru.practicum.explore.model.UpdateEventUserRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {
    List<EventShortDto> getEventsByUserId(Long userId, Integer from, Integer size);

    EventFullDto postEventByUserId(Long userId, NewEventDto newEventDto);

    EventFullDto getEventByUserIdAndEventId(Long userId, Long eventId);

    EventFullDto updateEvent(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest);

    List<ParticipationRequestDto> getParticipationRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult changeRequestState(Long userId, Long eventId, EventRequestStatusUpdateRequest request);

    List<EventFullDto> getEventsBySearchParams(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, Integer from, Integer size);

    EventFullDto updateEventByAdminRequest(Long eventId, UpdateEventAdminRequest request);

    List<EventShortDto> getEventsByUserBySearchParams(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, SortEnum sort, Integer from, Integer size, HttpServletRequest request);

    EventFullDto getEventById(Long eventId, HttpServletRequest request);
}
