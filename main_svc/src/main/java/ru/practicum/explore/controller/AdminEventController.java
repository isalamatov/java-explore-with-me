package ru.practicum.explore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.dto.EventFullDto;
import ru.practicum.explore.model.UpdateEventAdminRequest;
import ru.practicum.explore.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminEventController {
    private final EventService eventService;

    @GetMapping("/events")
    public List<EventFullDto> getEventsBySearchParams(@RequestParam List<Long> users,
                                                      @RequestParam(required = false) List<String> states,
                                                      @RequestParam(required = false) List<Long> categories,
                                                      @RequestParam(required = false, defaultValue = "")
                                                      String rangeStart,
                                                      @RequestParam(required = false, defaultValue = "")
                                                      String rangeEnd,
                                                      @RequestParam(required = false, defaultValue = "0")
                                                      @PositiveOrZero Integer from,
                                                      @RequestParam(required = false, defaultValue = "25")
                                                      @PositiveOrZero Integer size) {
        log.debug("Search events request received in controller {}", this.getClass());
        List<EventFullDto> eventFullDtos = eventService.getEventsBySearchParams(users,
                states,
                categories,
                rangeStart,
                rangeEnd,
                from,
                size);
        log.debug("Search events request processed successfully in controller {}", this.getClass());
        return eventFullDtos;
    }

    @PatchMapping("/events/{eventId}")
    public EventFullDto updateEventByAdminRequest(@PathVariable Long eventId,
                                                  @RequestBody @Valid UpdateEventAdminRequest request) {
        log.debug("Update event request received in controller {}", this.getClass());
        EventFullDto eventFullDto = eventService.updateEventByAdminRequest(eventId, request);
        log.debug("Update event request processed successfully in controller {}", this.getClass());
        return eventFullDto;
    }
}
