package com.crypto.wallet.scheduler.deferredevent;

import com.crypto.wallet.entity.deferevent.DeferredEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface DeferredEventCommand {
    void execute(DeferredEvent deferredEvent) throws Exception;
    default void postprocess(){

    }
}
