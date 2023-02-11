package ru.practicum.explore.exceptions;

public class ConflictException extends RuntimeException{
    public <T> ConflictException(Class<T> clazz) {
        super(String.format("Conflict while creating entity of class: %s", clazz));
    }
}
