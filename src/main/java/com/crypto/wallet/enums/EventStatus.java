package com.crypto.wallet.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum EventStatus {
    CREATED("CREATED"),
    INPROGRESS("INPROGRESS"),
    PROCESSED("PROCESSED");

    private final String value;

    EventStatus(String value) {
        this.value = value;
    }

    public static EventStatus fromString(String eventStatus) {
        return Arrays.stream(EventStatus.values())
                .filter(status -> status.getValue().equalsIgnoreCase(eventStatus)).findFirst().orElse(null);
    }

    public static boolean in(String eventStatus) {
        return Objects.nonNull(fromString(eventStatus));
    }
}
