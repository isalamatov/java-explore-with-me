package ru.practicum.explore.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@Jacksonized
public class EndpointHitDto {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;

    @Override
    public String toString() {
        return "{\"id\":" + id +
                ", \"app\":\"" + app +
                "\",\"uri\":\"" + uri +
                "\", \"ip\":\"" + ip +
                "\", \"timestamp\":\"" + timestamp + "\"}";
    }
}
