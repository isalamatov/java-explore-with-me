package ru.practicum.explore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

/**
 * Request to take attend an event
 */
@Data
@Accessors(chain = true)
@Validated
public class ParticipationRequestDto {
    @JsonProperty("created")
    private String created;
    @JsonProperty("event")
    private Long event;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("requester")
    private Long requester;
    @JsonProperty("status")
    private String status;
}
