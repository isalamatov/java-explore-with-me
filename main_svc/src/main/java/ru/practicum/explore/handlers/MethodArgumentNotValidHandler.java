package ru.practicum.explore.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.practicum.explore.model.ApiError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class MethodArgumentNotValidHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity<ApiError> handleNotValidExceptions(final MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (ex.getMessage().contains("New event date and time should be in format of pattern yyyy-MM-dd HH:mm:ss")) {
            status = HttpStatus.CONFLICT;
        }
        ApiError error = new ApiError(List.of(Objects.requireNonNull(ex.getMessage())),
                ex.getMessage(),
                ex.getObjectName(),
                status,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return new ResponseEntity<>(error, status);
    }
}
