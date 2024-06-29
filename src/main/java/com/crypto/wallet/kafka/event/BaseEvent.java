package com.crypto.wallet.kafka.event;

import lombok.Data;

@Data
public class BaseEvent {
    private String eventType;
    private String eventSource;
}
