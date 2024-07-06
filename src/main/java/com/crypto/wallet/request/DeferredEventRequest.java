package com.crypto.wallet.request;

import com.crypto.wallet.enums.EventStatus;
import com.crypto.wallet.utils.CommonConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeferredEventRequest {
    private String eventType;
    private EventStatus eventStatus;
    private String eventSource;
    private String  payload;
    private int maxRetrycount= CommonConstants.DEFAULT_MAX_RETRIES;
    private long deferUntil;
}
