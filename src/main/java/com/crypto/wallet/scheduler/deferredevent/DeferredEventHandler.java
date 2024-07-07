package com.crypto.wallet.scheduler.deferredevent;

import com.crypto.wallet.entity.deferevent.DeferredEvent;
import com.crypto.wallet.enums.deferredevent.EventStatus;
import com.crypto.wallet.enums.deferredevent.EventType;
import com.crypto.wallet.service.DeferEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.AddOperation;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DeferredEventHandler {
    private static final Logger logger = LoggerFactory.getLogger(DeferredEventHandler.class);
    private final DeferredEventCommandInvoker invoker;
    private final ObjectMapper objectMapper;
    private final DeferEventService deferEventService;

    @Async
    public void processDeferredEvent(DeferredEvent eligibleDeferredEvent) throws Exception {
        List<JsonPatchOperation> operations = new ArrayList<>();
        boolean exceptionOcurred = false;
        try{
            invoker.execute(eligibleDeferredEvent);
        }catch(Exception ex){
            //Exception handling and the post process of the deferred event is pending
            logger.info("Exception while handling the deferred Event",ex);
            if(eligibleDeferredEvent.getRetryCount() >= eligibleDeferredEvent.getMaxRetryCount()){
                operations.add(new ReplaceOperation(JsonPointer.of("eventStatus")
                        ,objectMapper.readTree(EventStatus.FAILED.getValue())));
            }else{
                operations.add(new ReplaceOperation(JsonPointer.of("retryCount"),
                        objectMapper.readTree(String.valueOf(eligibleDeferredEvent.getRetryCount()+1))));
                operations.add(new ReplaceOperation(JsonPointer.of("eventStatus"),
                        objectMapper.readTree(EventStatus.INPROGRESS.getValue())));
            }
            exceptionOcurred=true;
        }
        if(!exceptionOcurred){
            operations.add(new ReplaceOperation(JsonPointer.of("eventStatus")
                    ,objectMapper.readTree(EventStatus.PROCESSED.getValue())));
        }
        List<JsonPatch> updatedPatchs = operations.stream().map(operation->new JsonPatch(List.of(operation))).toList();
        deferEventService.applyUpdatesOnDeferredEvent(eligibleDeferredEvent.getEventTuid(),updatedPatchs);
    }



}

//@Id
//@GeneratedValue(strategy = GenerationType.UUID)
//@Column(name = "event_tuid", unique = true, nullable = false)
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