package ru.practicum.explore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;
import ru.practicum.explore.model.Location;
import ru.practicum.explore.validator.NewEventDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * New event
 */
@Data
@Accessors(chain = true)
@Validated
public class NewEventDto {
    @JsonProperty("annotation")
    @Size(min = 20, max = 2000)
    private String annotation = null;
    @JsonProperty("category")
    private Long category = null;
    @JsonProperty("description")
    @Size(min = 20, max = 7000)
    @NotNull
    private String description;
    @JsonProperty("eventDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NewEventDate
    private LocalDateTime eventDate = null;
    @JsonProperty("location")
    private Location location = null;
    @JsonProperty("paid")
    private Boolean paid = null;
    @JsonProperty("participantLimit")
    private Integer participantLimit = 0;
    @JsonProperty("requestModeration")
    private Boolean requestModeration = true;
    @JsonProperty("title")
    @NotNull
    @Size(min = 3, max = 120)
    private String title = null;
}
