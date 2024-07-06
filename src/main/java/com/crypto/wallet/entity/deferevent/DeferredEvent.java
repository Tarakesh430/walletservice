package com.crypto.wallet.entity.deferevent;

import com.crypto.wallet.enums.deferredevent.EventStatus;
import com.crypto.wallet.enums.deferredevent.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "deferevents")
@Table(name = "deferevents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeferredEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "event_tuid", unique = true, nullable = false)
    private String eventTuid;
    @Column(name = "event_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    @Column(name = "event_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;
    private String eventSource;
    @Column(name = "payload", columnDefinition = "json")
    private String payload;
    private long deferUntil;
    private long createdTime;
    private long updatedTime;
    private int retryCount;
    private int maxRetryCount;
}
