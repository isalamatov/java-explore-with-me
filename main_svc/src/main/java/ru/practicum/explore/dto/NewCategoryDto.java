package ru.practicum.explore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

/**
 * Name of new category to add
 */
@Data
@Accessors(chain = true)
@Validated
public class NewCategoryDto {
    @JsonProperty("name")
    private String name;
}
