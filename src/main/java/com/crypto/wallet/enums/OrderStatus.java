package com.crypto.wallet.enums;

import lombok.Getter;

import java.util.*;

@Getter
public enum OrderStatus {
    CREATED("CREATED"),
    PROCESSED("PROCESSED"),
    CANCELLED("CANCELLED"),
    INPROGRESS("INPROGRESS");


    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public static OrderStatus fromString(String orderStatus) {
        return Arrays.stream(OrderStatus.values())
                .filter(status -> status.getValue().equalsIgnoreCase(orderStatus)).findFirst().orElse(null);
    }

    public static boolean in(String orderStatus) {
        return Objects.nonNull(fromString(orderStatus));
    }
    private static final Map<OrderStatus, Set<OrderStatus>> VALID_TRANSITIONS = new HashMap<>();

    static {
        VALID_TRANSITIONS.put(CREATED, EnumSet.of(PROCESSED, CANCELLED, INPROGRESS));
        VALID_TRANSITIONS.put(PROCESSED, EnumSet.noneOf(OrderStatus.class));
        VALID_TRANSITIONS.put(CANCELLED, EnumSet.noneOf(OrderStatus.class)); // No valid transitions from CANCELLED
        VALID_TRANSITIONS.put(INPROGRESS, EnumSet.of(PROCESSED, CANCELLED));
    }
    public static boolean isValidTransition(OrderStatus from, OrderStatus to) {
        return VALID_TRANSITIONS.get(from).contains(to);
    }
}
