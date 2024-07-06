package com.crypto.wallet.service;

import com.common.library.utils.CommonUtils;
import com.crypto.wallet.controller.DeferredEventResource;
import com.crypto.wallet.entity.deferevent.DeferredEvent;
import com.crypto.wallet.helper.DeferredEventHelper;
import com.crypto.wallet.repository.DeferredEventRepository;
import com.crypto.wallet.request.DeferredEventRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeferEventService {
    private static final Logger logger = LoggerFactory.getLogger(DeferredEventResource.class);
    private final DeferredEventHelper deferredEventHelper;
    private final DeferredEventRepository deferredEventRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public DeferredEvent createNewDeferredEvent(DeferredEventRequest request) throws Exception {
        validateDeferredEventRequest(request);
        DeferredEvent newDeferredEvent = deferredEventHelper.createNewDeferredEvent(request);
        DeferredEvent savedDeferredEvent = deferredEventRepository.save(newDeferredEvent);
        logger.info("Successfully Saved DeferredEvent {}",savedDeferredEvent);
        return savedDeferredEvent;
    }

    private void validateDeferredEventRequest(DeferredEventRequest request) throws Exception {
        //event type ,event source should not be null
        if (StringUtils.isBlank(request.getEventType())) {
            throw new Exception("Event Type should not be null");
        }
        if (StringUtils.isBlank(request.getEventSource())) {
            throw new Exception("event source should not be null");
        }
        // the defer Until Time should be greater than or equal to current timestamp
        if (request.getDeferUntil() < CommonUtils.getEpochTimeStamp()) {
            throw new Exception("the defer Until Time should be greater than current timestamp");
        }

    }

    public DeferredEvent getDeferredEventDetails(String eventId) throws Exception {
        logger.info("Get Deferred Event Details for the event Id {} ", eventId);
        DeferredEvent deferredEvent = deferredEventRepository.findById(eventId)
                .orElseThrow(() -> new Exception("event Id not found"));
        logger.info("Deferred event Id found {}",deferredEvent);
        return deferredEvent;
    }


    public DeferredEvent applyUpdatesOnDeferredEvent(String eventId, List<JsonPatch> updatePatches) throws Exception {
        logger.info("Apply Patch Updates on the defered Events");
        DeferredEvent oldDeferredEvent = deferredEventRepository.findById(eventId)
                .orElseThrow(() -> new Exception("event Id not found"));
        JsonNode oldDeferredEventJsonNode = objectMapper.convertValue(oldDeferredEvent,JsonNode.class);
        JsonNode patchedDeferredEventJsonNode = CommonUtils.applyJsonPatches(updatePatches, oldDeferredEventJsonNode);
        DeferredEvent patchedDeferredEvent = objectMapper.treeToValue(patchedDeferredEventJsonNode, DeferredEvent.class);
        // Allow change in deferUntil Time
        oldDeferredEvent.setMaxRetryCount(patchedDeferredEvent.getMaxRetryCount());
        oldDeferredEvent.setRetryCount(patchedDeferredEvent.getRetryCount());
        oldDeferredEvent.setEventType(patchedDeferredEvent.getEventType());
        oldDeferredEvent.setEventStatus(patchedDeferredEvent.getEventStatus());
        oldDeferredEvent.setUpdatedTime(CommonUtils.getEpochTimeStamp());
        oldDeferredEvent.setEventSource(patchedDeferredEvent.getEventSource());
        oldDeferredEvent.setPayload(patchedDeferredEvent.getPayload());
        oldDeferredEvent.setDeferUntil(patchedDeferredEvent.getDeferUntil());
        DeferredEvent savedDeferredEvent = deferredEventRepository.save(oldDeferredEvent);
        logger.info("Saved DeferredEvent {} ",savedDeferredEvent);
        return savedDeferredEvent;
    }
}
