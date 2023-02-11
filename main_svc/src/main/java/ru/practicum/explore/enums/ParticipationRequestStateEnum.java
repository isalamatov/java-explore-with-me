package ru.practicum.explore.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ParticipationRequestStateEnum {
    PENDING("PENDING"),
    CONFIRMED("CONFIRMED"),
    CANCELED("CANCELED"),
    REJECTED("REJECTED");

    private String value;

    ParticipationRequestStateEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static ParticipationRequestStateEnum fromValue(String text) {
        for (ParticipationRequestStateEnum b : ParticipationRequestStateEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
