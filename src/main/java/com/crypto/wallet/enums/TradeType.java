package com.crypto.wallet.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum TradeType {
    BUY("BUY"),
    SELL("SELL");

    private final String value;

    TradeType(String value) {
        this.value = value;
    }

    public static TradeType fromString(String type) {
        return Arrays.stream(TradeType.values())
                .filter(status -> status.getValue().equalsIgnoreCase(type)).findFirst().orElse(null);
    }

    public static boolean in(String type) {
        return Objects.nonNull(fromString(type));
    }
}
