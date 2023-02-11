package ru.practicum.explore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Embeddable;

/**
 * Latitude and longitude of the event location
 */
@Data
@Accessors(chain = true)
@Validated
@Embeddable
public class Location {
    @JsonProperty("lat")
    private Float lat = null;
    @JsonProperty("lon")
    private Float lon = null;
}
