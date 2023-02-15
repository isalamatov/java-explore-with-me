package ru.practicum.explore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * Compelation of events DTO
 */
@Validated
@Data
public class CompilationDto {
    @JsonProperty("events")
    @Valid
    private List<EventShortDto> events;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("pinned")
    private Boolean pinned;
    @JsonProperty("title")
    private String title;
}
