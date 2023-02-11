package ru.practicum.explore.validator;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

@Slf4j
public class NewEventDateValidator implements ConstraintValidator<NewEventDate, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime eventDate, ConstraintValidatorContext constraintValidatorContext) {
        if (eventDate == null) {
            return true;
        }
        LocalDateTime minDate = LocalDateTime.now();
        boolean result = eventDate.isAfter(minDate.plusHours(2));
        log.debug("Validation result of field value {} : {}", eventDate, result);
        return result;
    }
}
