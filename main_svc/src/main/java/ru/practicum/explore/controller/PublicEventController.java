package ru.practicum.explore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explore.dto.EventFullDto;
import ru.practicum.explore.dto.EventShortDto;
import ru.practicum.explore.enums.SortEnum;
import ru.practicum.explore.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PublicEventController {
    private final EventService eventService;

    @GetMapping("/events")
    public List<EventShortDto> getEventsByUserBySearchParams(@RequestParam(required = false, defaultValue = "")
                                                             String text,
                                                             @RequestParam
                                                             List<Long> categories,
                                                             @RequestParam
                                                             Boolean paid,
                                                             @RequestParam(required = false, defaultValue = "")
                                                             String rangeStart,
                                                             @RequestParam(required = false, defaultValue = "")
                                                             String rangeEnd,
                                                             @RequestParam(required = false, defaultValue = "true")
                                                             Boolean onlyAvailable,
                                                             @RequestParam(required = false, defaultValue = "EVENT_DATE")
                                                             SortEnum sort,
                                                             @RequestParam(required = false, defaultValue = "0")
                                                             @PositiveOrZero Integer from,
                                                             @RequestParam(required = false, defaultValue = "25")
                                                             @Positive Integer size,
                                                             HttpServletRequest request) {
        log.debug("Search events request received in controller {}", this.getClass());
        List<EventShortDto> eventShortDtos = eventService.getEventsByUserBySearchParams(text,
                categories,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                sort,
                from,
                size,
                request);
        log.debug("Search events request processed successfully in controller {}", this.getClass());
        return eventShortDtos;
    }

    @GetMapping("/events/{eventId}")
    public EventFullDto getEventById(@PathVariable Long eventId, HttpServletRequest request) {
        log.debug("Get event {} request received in controller {}", eventId, this.getClass());
        EventFullDto event = eventService.getEventById(eventId, request);
        log.debug("Get event {} request processed in controller {}", eventId, this.getClass());
        return event;
    }
}
