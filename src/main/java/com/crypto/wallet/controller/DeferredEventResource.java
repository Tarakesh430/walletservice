package com.crypto.wallet.controller;

import com.common.library.response.ApiResponse;
import com.crypto.wallet.entity.deferevent.DeferredEvent;
import com.crypto.wallet.request.DeferredEventRequest;
import com.crypto.wallet.service.DeferEventService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/deferredevent")
@RequiredArgsConstructor
public class DeferredEventResource {
    private static final Logger logger = LoggerFactory.getLogger(DeferredEventResource.class);
    private final DeferEventService deferEventService;
    @PostMapping
    public ResponseEntity<ApiResponse<DeferredEvent>> createDeferredEvent(@RequestBody DeferredEventRequest deferredEventRequest) throws Exception {
        logger.info("POST :: CREATE NEW DEFERRED EVENT:: CREATE");
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Successfully Created DeferredEvent",
            deferEventService.createNewDeferredEvent(deferredEventRequest)));

    }
    @GetMapping("/{event-id}")
    public ResponseEntity<ApiResponse<DeferredEvent>> getDeferredEvent(@PathVariable("event-id") String eventId) throws Exception {
        logger.info("GET :: DEFERRED EVENT DETAILS :: GET");
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Successfully Created DeferredEvent",
                deferEventService.getDeferredEventDetails(eventId)));
    }

    @PatchMapping("/{event-id}")
    public ResponseEntity<ApiResponse<DeferredEvent>> updateDeferredEvent(@PathVariable("event-id") String eventId,
                                                                          List<JsonPatch> updatePatches) throws Exception {
        logger.info("UPDATE :: PATCH DEFERRED EVENT:: UPDATES");
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Successfully Created DeferredEvent",
                deferEventService.applyUpdatesOnDeferredEvent(eventId,updatePatches)));
    }
}
