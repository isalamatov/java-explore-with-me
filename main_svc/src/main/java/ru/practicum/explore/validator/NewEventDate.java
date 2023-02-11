package ru.practicum.explore.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NewEventDateValidator.class)

public @interface NewEventDate {
    String message() default "New event date and time should be in format of pattern yyyy-MM-dd HH:mm:ss " +
            "and be minimum 2 hours later than creation date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
