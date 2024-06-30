package com.crypto.wallet.kafka.postprocess.impl;

import com.common.library.events.OrderEvent;
import com.crypto.wallet.enums.OrderStatus;
import com.crypto.wallet.kafka.postprocess.BasePostProcess;
import com.crypto.wallet.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.crypto.wallet.utils.CommonConstants.PROCESS_ORDER_EVENT;

@Component("orderEventPostProcess")
@RequiredArgsConstructor
public class OrderEventPostProcess implements BasePostProcess<OrderEvent> {
    private final Logger logger = LoggerFactory.getLogger(OrderEventPostProcess.class);
    private final OrderRepository orderRepository;

    @Transactional
    public void postEventProcess(OrderEvent orderEvent) {
        logger.info("Post Process Order Event");
        if (orderEvent.getEventType().equals(PROCESS_ORDER_EVENT)) {
            handleProcessOrderCreationEvent(orderEvent);
        } else {
            logger.info("No actions Performed in Post Process for OrderEvent");
        }
    }

    private void handleProcessOrderCreationEvent(OrderEvent orderEvent) {
        orderRepository.updateOrderStatus(OrderStatus.CREATED, OrderStatus.INPROGRESS,
                orderEvent.getOrderId());
        logger.info("Update Order status to InProgress as the Order published to queue {}",orderEvent.getOrderId());

    }
}
