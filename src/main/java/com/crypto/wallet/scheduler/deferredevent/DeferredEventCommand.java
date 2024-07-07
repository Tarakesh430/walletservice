package com.crypto.wallet.scheduler.deferredevent;

import com.crypto.wallet.entity.deferevent.DeferredEvent;

public interface DeferredEventCommand {
    void execute(DeferredEvent deferredEvent) throws Exception;
}
