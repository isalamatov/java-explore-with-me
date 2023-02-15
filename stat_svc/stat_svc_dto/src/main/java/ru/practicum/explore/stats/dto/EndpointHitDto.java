package ru.practicum.explore.stats.dto;

import lombok.Data;

@Data
public class EndpointHitDto {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
