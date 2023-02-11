package ru.practicum.explore.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StateActionEnum {
    PUBLISH_EVENT("PUBLISH_EVENT"),
    REJECT_EVENT("REJECT_EVENT"),
    SEND_TO_REVIEW("SEND_TO_REVIEW"),
    CANCEL_REVIEW("CANCEL_REVIEW");

    private String value;

    StateActionEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static StateActionEnum fromValue(String text) {
        for (StateActionEnum b : StateActionEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
