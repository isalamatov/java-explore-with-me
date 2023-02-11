package ru.practicum.explore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

/**
 * Краткая информация о событии
 */
@Data
@Accessors(chain = true)
@Validated
public class EventShortDto {
    @JsonProperty("annotation")
    private String annotation;
    @JsonProperty("category")
    private CategoryDto category;
    @JsonProperty("confirmedRequests")
    private Integer confirmedRequests;
    @JsonProperty("eventDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("initiator")
    private UserShortDto initiator;
    @JsonProperty("paid")
    private Boolean paid;
    @JsonProperty("title")
    private String title;
    @JsonProperty("views")
    private Long views;
}
