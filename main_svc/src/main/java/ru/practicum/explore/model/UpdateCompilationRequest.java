package ru.practicum.explore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * Event compilation information change. Fields may be null, if change is not needed.
 */
@Data
@Accessors(chain = true)
@Validated
public class UpdateCompilationRequest {
    @JsonProperty("events")
    @Valid
    private List<Long> events = null;
    @JsonProperty("pinned")
    private Boolean pinned = null;
    @JsonProperty("title")
    private String title = null;
}
