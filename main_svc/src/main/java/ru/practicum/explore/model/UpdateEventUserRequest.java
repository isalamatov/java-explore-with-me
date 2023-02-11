package ru.practicum.explore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;
import ru.practicum.explore.enums.StateActionEnum;
import ru.practicum.explore.validator.NewEventDate;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Data to change event information. Fields may be null, if change is not needed.
 */
@Data
@Accessors(chain = true)
@Validated
public class UpdateEventUserRequest {
    @JsonProperty("annotation")
    @Size(min = 20, max = 2000)
    private String annotation = null;
    @JsonProperty("category")
    private Long category = null;
    @JsonProperty("description")
    @Size(min = 20, max = 7000)
    private String description = null;
    @JsonProperty("eventDate")
    @NewEventDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private LocalDateTime eventDate = null;
    @JsonProperty("location")
    @Valid
    private Location location = null;
    @JsonProperty("paid")
    private Boolean paid = null;
    @JsonProperty("participantLimit")
    private Integer participantLimit = null;
    @JsonProperty("requestModeration")
    private Boolean requestModeration = null;
    @JsonProperty("stateAction")
    private StateActionEnum stateAction = null;
    @JsonProperty("title")
    @Size(min = 3, max = 120)
    private String title = null;
}
