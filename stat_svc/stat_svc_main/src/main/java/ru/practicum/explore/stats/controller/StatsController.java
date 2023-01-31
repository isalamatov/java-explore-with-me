package ru.practicum.explore.stats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.stats.dto.EndpointHitDto;
import ru.practicum.explore.stats.dto.ViewStatsDto;
import ru.practicum.explore.stats.service.StatsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void hit(@RequestBody EndpointHitDto endpointHitDto) {
        log.debug("Endpoint hit {} received by controller {}", endpointHitDto, this.getClass());
        statsService.hit(endpointHitDto);
        log.debug("Endpoint hit processed by controller {}", this.getClass());
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> get(@RequestParam String start,
                                  @RequestParam String end,
                                  @RequestParam List<String> uris,
                                  @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        log.debug("Stats request received by controller {}", this.getClass());
        List<ViewStatsDto> viewStatsDtos = statsService.get(start, end, uris, unique);
        log.debug("Endpoint hit processed by controller {}", this.getClass());
        return viewStatsDtos;
    }

}
