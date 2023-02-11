package ru.practicum.explore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Events compilation
 */
@Data
@Accessors(chain = true)
@Validated
public class NewCompilationDto {
    @JsonProperty("events")
    @Valid
    private List<Long> events = null;
    @JsonProperty("pinned")
    private Boolean pinned = false;
    @JsonProperty("title")
    @NotNull
    private String title = null;
}
