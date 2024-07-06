package com.crypto.wallet.helper;

import com.common.library.utils.CommonUtils;
import com.crypto.wallet.entity.deferevent.DeferredEvent;
import com.crypto.wallet.enums.deferredevent.EventStatus;
import com.crypto.wallet.enums.deferredevent.EventType;
import com.crypto.wallet.request.DeferredEventRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeferredEventHelper {
    private static final Logger logger = LoggerFactory.getLogger(DeferredEventHelper.class);
    public DeferredEvent createNewDeferredEvent(DeferredEventRequest request){
        DeferredEvent deferredEvent = new DeferredEvent();
        deferredEvent.setEventStatus(EventStatus.CREATED);
        deferredEvent.setEventSource(request.getEventSource());
        deferredEvent.setEventType(EventType.fromString(request.getEventType()));
        deferredEvent.setCreatedTime(CommonUtils.getEpochTimeStamp());
        deferredEvent.setUpdatedTime(CommonUtils.getEpochTimeStamp());
        deferredEvent.setPayload(request.getPayload());
        deferredEvent.setMaxRetryCount(request.getMaxRetrycount());
        deferredEvent.setDeferUntil(request.getDeferUntil());
        return deferredEvent;
    }
}
