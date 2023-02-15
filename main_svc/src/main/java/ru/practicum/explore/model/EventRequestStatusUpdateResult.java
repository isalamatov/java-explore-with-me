package ru.practicum.explore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;
import ru.practicum.explore.dto.ParticipationRequestDto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * The result of confirming/rejecting appliances to take part in event
 */
@Data
@Accessors(chain = true)
@Validated
public class EventRequestStatusUpdateResult {
    @JsonProperty("confirmedRequests")
    @Valid
    private List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
    @JsonProperty("rejectedRequests")
    @Valid
    private List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();
}
