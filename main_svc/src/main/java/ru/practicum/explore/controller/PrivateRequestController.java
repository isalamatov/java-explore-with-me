package ru.practicum.explore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.dto.ParticipationRequestDto;
import ru.practicum.explore.service.RequestService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PrivateRequestController {
    private final RequestService requestService;

    @GetMapping("/users/{userId}/requests")
    public List<ParticipationRequestDto> getUserRequests(@PathVariable Long userId) {
        log.debug("Get participation request received in controller {}", this.getClass());
        List<ParticipationRequestDto> requestDtos = requestService.getUserRequests(userId);
        log.debug("Get participation request processed in controller {}", this.getClass());
        return requestDtos;
    }

    @PostMapping("/users/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createParticipationRequest(@PathVariable Long userId,
                                                              @RequestParam Long eventId) {
        log.debug("Create participation request received in controller {}", this.getClass());
        ParticipationRequestDto request = requestService.createParticipationRequest(userId, eventId);
        log.debug("Create participation request processed in controller {}", this.getClass());
        return request;
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Long userId,
                                                 @PathVariable Long requestId) {
        log.debug("Create participation request received in controller {}", this.getClass());
        ParticipationRequestDto request = requestService.cancelRequest(userId, requestId);
        log.debug("Create participation request processed in controller {}", this.getClass());
        return request;
    }
}
