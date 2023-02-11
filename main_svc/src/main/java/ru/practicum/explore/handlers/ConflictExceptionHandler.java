package ru.practicum.explore.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.explore.exceptions.ConflictException;
import ru.practicum.explore.exceptions.ForbiddenConditionsException;
import ru.practicum.explore.model.ApiError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class ConflictExceptionHandler {
    @ExceptionHandler({ConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handlConflictExceptions(final ConflictException ex) {
        return new ApiError(List.of(Objects.requireNonNull(ex.getMessage())),
                ex.getMessage(),
                ex.getMessage(),
                HttpStatus.CONFLICT,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    @ExceptionHandler({ForbiddenConditionsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiError handleForbiddenExceptions(final ForbiddenConditionsException ex) {
        return new ApiError(List.of(Objects.requireNonNull(ex.getMessage())),
                ex.getMessage(),
                ex.getMessage(),
                HttpStatus.CONFLICT,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
}
