package com.crypto.wallet.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public enum Type {
    CRYPTO("CRYPTO"),
    STOCK("STOCK");

    private final String value;

    Type(String value) {
        this.value = value;
    }

    public static Type fromString(String type) {
        return Arrays.stream(Type.values())
                .filter(status -> status.getValue().equalsIgnoreCase(type)).findFirst().orElse(null);
    }

    public static boolean in(String type) {
        return Objects.nonNull(fromString(type));
    }
}

