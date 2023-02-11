package ru.practicum.explore.client;

import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.explore.stats.dto.EndpointHitDto;
import ru.practicum.explore.stats.dto.ViewStatsDto;

import java.util.List;

@FeignClient("stats")
public interface StatsClient {

    @RequestLine("GET /hit")
    void hit(@RequestBody EndpointHitDto endpointHitDto);

    @RequestLine("GET /stats?start={start}&end={end}&uris={uris}&unique={unique}")
    List<ViewStatsDto> get(@Param("start") String start,
                           @Param("end") String end,
                           @Param("uris") List<String> uris,
                           @Param("unique") Boolean unique);
}
