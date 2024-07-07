package com.crypto.wallet.scheduler;

import com.common.library.utils.CommonUtils;
import com.crypto.wallet.entity.deferevent.DeferredEvent;
import com.crypto.wallet.enums.deferredevent.EventStatus;
import com.crypto.wallet.enums.deferredevent.EventType;
import com.crypto.wallet.repository.DeferredEventRepository;
import com.crypto.wallet.scheduler.deferredevent.DeferredEventCommand;
import com.crypto.wallet.scheduler.deferredevent.DeferredEventCommandInvoker;
import com.crypto.wallet.scheduler.deferredevent.DeferredEventHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeferredEventPublisher {
    private static final Logger logger = LoggerFactory.getLogger(DeferredEventPublisher.class);
    private final DeferredEventRepository deferredEventRepository;
    private final DeferredEventHandler deferredEventHandler;

    @Scheduled(cron = "2 * * * * *")
    @Transactional
    public void performTask() throws Exception {
        logger.info("Get all the deferredEvents which are in CREATED MODE and deferUntil time ");
        List<DeferredEvent> eligibleDeferredEvents = deferredEventRepository.findEligibleDeferredEvents(
                Arrays.asList(EventStatus.CREATED,EventStatus.INPROGRESS), CommonUtils.getEpochTimeStamp());
        logger.info("Eligible Deferred Events ready to publish {}",eligibleDeferredEvents.size());
        for (DeferredEvent eligibleDeferredEvent : eligibleDeferredEvents) {
            deferredEventHandler.processDeferredEvent(eligibleDeferredEvent);
        }
    }
}
//private String eventTuid;
//@Column(name = "event_type", nullable = false)
//@Enumerated(EnumType.STRING)
//private EventType eventType;
//@Column(name = "event_status", nullable = false)
//@Enumerated(EnumType.STRING)
//private EventStatus eventStatus;
//private String eventSource;
//@Column(name = "payload", columnDefinition = "json")
//private String payload;
//private long deferUntil;
//private long createdTime;
//private long updatedTime;
//private int retryCount;
//private int maxRetryCount;