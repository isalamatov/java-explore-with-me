package ru.practicum.explore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Short information about users
 */
@Data
@Accessors(chain = true)
@Validated
public class UserShortDto {
    @JsonProperty("id")
    @NotNull
    private Long id = null;
    @JsonProperty("name")
    @NotNull
    private String name = null;
}
