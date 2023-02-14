package ru.practicum.explore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.practicum.explore.enums.EventStateEnum;
import ru.practicum.explore.model.Location;

import java.time.LocalDateTime;
import java.util.List;

/**
 * EventFullDto
 */
@Data
@Validated
public class EventFullDto {
    @JsonProperty("annotation")
    private String annotation;
    @JsonProperty("category")
    private CategoryDto category;
    @JsonProperty("confirmedRequests")
    private Integer confirmedRequests;
    @JsonProperty("createdOn")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @JsonProperty("description")
    private String description;
    @JsonProperty("eventDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("initiator")
    private UserShortDto initiator;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("paid")
    private Boolean paid;
    @JsonProperty("participantLimit")
    private Integer participantLimit;
    @JsonProperty("publishedOn")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    @JsonProperty("requestModeration")
    private Boolean requestModeration;
    @JsonProperty("state")
    private EventStateEnum state;
    @JsonProperty("title")
    private String title;
    @JsonProperty("views")
    private Long views;
    @JsonProperty("rating")
    private Long rating;
    @JsonProperty("likedBy")
    private List<UserShortDto> likedBy;
}
