package ru.practicum.explore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * Category DTO
 */
@Data
@Accessors(chain = true)
public class CategoryDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    @NotBlank
    private String name;
}
