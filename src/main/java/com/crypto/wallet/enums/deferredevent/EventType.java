package com.crypto.wallet.enums.deferredevent;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum EventType {
    ORDER_CREATION_EVENT("ORDER_CREATE"),
    NOTIFICATION_EVENT("NOTIFICATION_EVENT");

    private final String value;

    EventType(String value) {
        this.value = value;
    }

    public static EventType fromString(String eventType) {
        return Arrays.stream(EventType.values())
                .filter(status -> status.getValue().equalsIgnoreCase(eventType)).findFirst().orElse(null);
    }

    public static boolean in(String eventType) {
        return Objects.nonNull(fromString(eventType));
    }
}
