package ru.practicum.explore.stats.service;

import ru.practicum.explore.stats.dto.EndpointHitDto;
import ru.practicum.explore.stats.dto.ViewStatsDto;

import java.util.List;

public interface StatsService {
    void hit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> get(String start, String end, List<String> uris, Boolean unique);
}
