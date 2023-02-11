package ru.practicum.explore.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

/**
 * Data to change event information. Fields may be null, if change is not needed.
 */
@Data
@Accessors(chain = true)
@Validated
public class UpdateEventAdminRequest extends UpdateEventUserRequest {
}
