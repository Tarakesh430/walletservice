package com.crypto.wallet.scheduler.deferredevent.impl;

import com.common.library.enums.TradeType;
import com.common.library.events.OrderEvent;
import com.crypto.wallet.entity.deferevent.DeferredEvent;
import com.crypto.wallet.entity.order.Order;
import com.crypto.wallet.enums.OrderStatus;
import com.crypto.wallet.request.OrderRequest;
import com.crypto.wallet.response.OrderResponse;
import com.crypto.wallet.scheduler.deferredevent.DeferredEventCommand;
import com.crypto.wallet.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderEventCommand implements DeferredEventCommand {
    private static final Logger logger = LoggerFactory.getLogger(OrderEventCommand.class);
    private final ObjectMapper objectMapper;
    private final OrderService orderService;
    @Override
    public void execute(DeferredEvent deferredEvent) throws Exception {
        OrderEvent orderEvent = objectMapper.readValue(deferredEvent.getPayload(), OrderEvent.class);
        OrderRequest orderRequest = prepareOrderRequest(orderEvent);
        OrderResponse orderResponse = orderService.placeOrder(orderRequest);
        logger.info("Successfully Placed order {}",orderResponse);
    }

    private OrderRequest prepareOrderRequest(OrderEvent orderEvent) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setExchangeName(orderEvent.getExchangeName());
        orderRequest.setWalletId(orderEvent.getWalletId());
        orderRequest.setPrice(orderEvent.getPrice());
        orderRequest.setQuantity(orderEvent.getQuantity());
        orderRequest.setRecurring(orderRequest.isRecurring());
        orderRequest.setStockName(orderRequest.getStockName());
        orderRequest.setTradeType(orderRequest.getTradeType());
        orderRequest.setOrderGroupId(orderRequest.getOrderGroupId());
        return orderRequest;
    }
}
