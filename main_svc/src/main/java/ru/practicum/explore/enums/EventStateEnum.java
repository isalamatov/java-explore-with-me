package ru.practicum.explore.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EventStateEnum {
    PENDING("PENDING"),

    PUBLISHED("PUBLISHED"),

    CANCELED("CANCELED");

    private String value;

    EventStateEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static EventStateEnum fromValue(String text) {
        for (EventStateEnum b : EventStateEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
