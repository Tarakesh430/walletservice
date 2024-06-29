package com.crypto.wallet.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

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
}
