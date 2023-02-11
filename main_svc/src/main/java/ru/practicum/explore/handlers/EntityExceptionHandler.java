package ru.practicum.explore.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.explore.exceptions.EntityDoesNotExistsException;

@ControllerAdvice
public class EntityExceptionHandler {
    @ExceptionHandler({EntityDoesNotExistsException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleNotFoundExceptions(final EntityDoesNotExistsException ex) {
        return ex.getMessage();
    }
}
