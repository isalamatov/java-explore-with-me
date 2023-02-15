package ru.practicum.explore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * Change in status of request to take part in the event of current user
 */
@Data
@Accessors(chain = true)
@Validated
public class EventRequestStatusUpdateRequest {
    @JsonProperty("requestIds")
    @Valid
    private List<Long> requestIds;
    @JsonProperty("status")
    private StatusEnum status;

    /**
     * State of request of change status
     */
    public enum StatusEnum {
        CONFIRMED("CONFIRMED"),

        REJECTED("REJECTED");

        private String value;

        StatusEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static StatusEnum fromValue(String text) {
            for (StatusEnum b : StatusEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }
}
