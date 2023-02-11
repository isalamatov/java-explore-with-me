package ru.practicum.explore.exceptions;

public class EntityDoesNotExistsException extends RuntimeException {
    public <T> EntityDoesNotExistsException(Class<T> clazz, long id) {
        super(String.format("Entity %s with id %d does not exist", clazz, id));
    }
}
