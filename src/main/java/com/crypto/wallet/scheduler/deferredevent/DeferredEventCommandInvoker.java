package com.crypto.wallet.scheduler.deferredevent;

import com.crypto.wallet.entity.deferevent.DeferredEvent;
import com.crypto.wallet.enums.deferredevent.EventType;
import com.crypto.wallet.scheduler.deferredevent.impl.OrderEventCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DeferredEventCommandInvoker {
    private static final Logger logger = LoggerFactory.getLogger(DeferredEventCommandInvoker.class);
    private final Map<EventType, DeferredEventCommand> commandMap = new HashMap<>();

    public DeferredEventCommandInvoker(List<DeferredEventCommand> commands) {
        for (DeferredEventCommand command : commands) {
            if (command instanceof OrderEventCommand) commandMap.put(EventType.ORDER_CREATION_EVENT, command);
        }
    }

    public void execute(DeferredEvent event) throws Exception {
        DeferredEventCommand command = commandMap.get(event.getEventType());
        if (command != null) {
            command.execute(event);
        } else {
            throw new IllegalArgumentException("No command found for event type: " + event.getEventType());
        }
    }
}
