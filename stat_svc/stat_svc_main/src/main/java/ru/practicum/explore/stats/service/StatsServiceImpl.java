package ru.practicum.explore.stats.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.explore.stats.dto.EndpointHitDto;
import ru.practicum.explore.stats.dto.ViewStatsDto;
import ru.practicum.explore.stats.mapper.EndpointHitMapper;
import ru.practicum.explore.stats.mapper.ViewStatsMapper;
import ru.practicum.explore.stats.model.ViewStats;
import ru.practicum.explore.stats.repository.StatsRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    private final EndpointHitMapper endpointHitMapper;

    private final ViewStatsMapper viewStatsMapper;

    @Override
    public void hit(EndpointHitDto endpointHitDto) {
        log.debug("Endpoint hit {} received by service {}", endpointHitDto, this.getClass());
        statsRepository.save(endpointHitMapper.toEntity(endpointHitDto));
        log.debug("Endpoint hit processed by service {}", this.getClass());
    }

    @Override
    public List<ViewStatsDto> get(String start, String end, List<String> uris, Boolean unique) {
        log.debug("Stats request received by service {}", this.getClass());
        List<ViewStats> viewStats = (!unique) ? statsRepository.getStats(start, end, uris)
                : statsRepository.getStatsDistinctIp(start, end, uris);
        log.debug("Stats request processed by service {}", this.getClass());
        return viewStatsMapper.toEntityList(viewStats);
    }
}
