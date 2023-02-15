package ru.practicum.explore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * User data transfer object
 */
@Data
@Accessors(chain = true)
@Validated
public class UserDto {
    @JsonProperty("id")
    private Long id = null;
    @JsonProperty("email")
    @Email
    @NotNull
    private String email;
    @JsonProperty("name")
    @NotNull
    private String name = null;
}
