package ru.practicum.explore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * New user data
 */
@Data
@Accessors(chain = true)
@Validated
public class NewUserRequest {
    @JsonProperty("email")
    @NotNull
    @Email
    private String email = null;
    @JsonProperty("name")
    @NotNull
    private String name = null;
}
