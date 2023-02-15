package ru.practicum.explore.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore.stats.model.EndpointHit;
import ru.practicum.explore.stats.model.ViewStats;

import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    @Query(nativeQuery = true, name = "getStats")
    List<ViewStats> getStats(String start, String end, List<String> uris);

    @Query(nativeQuery = true, name = "getStatsDistinctIp")
    List<ViewStats> getStatsDistinctIp(String start, String end, List<String> uris);
}
