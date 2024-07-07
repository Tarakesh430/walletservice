package com.crypto.wallet.scheduler;

import com.common.library.events.OrderEvent;
import com.crypto.wallet.entity.order.Order;
import com.crypto.wallet.enums.OrderStatus;
import com.crypto.wallet.kafka.KafkaProducer;
import com.crypto.wallet.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.crypto.wallet.utils.CommonConstants.EVENT_SOURCE;
import static com.crypto.wallet.utils.CommonConstants.PROCESS_ORDER_EVENT;

@Service
@RequiredArgsConstructor
public class OrderPublisher {
    private final Logger logger = LoggerFactory.getLogger(OrderPublisher.class);
    private final OrderRepository orderRepository;
    private final KafkaProducer kafkaProducer;

    @Scheduled(cron = "2 * * * * *")
    @Transactional
    public void performTask() {
        logger.info("Scheduled task running every 5 seconds");
        //Get the all the orders with status CREATED and there created time should be less than
        List<Order> orders =
                orderRepository.findCreatedOrdersByTimeAndStatus(0, OrderStatus.CREATED);
        if (CollectionUtils.isEmpty(orders)) {
            logger.info("No new Orders to be published for processing");
            return;
        }
        orders.stream().map(this :: createOrderEvent).forEach(kafkaProducer::sendMessage);
        logger.info("Orders of size {} being published",orders.size());
    }

    public OrderEvent createOrderEvent(Order order) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setEventSource(EVENT_SOURCE);
        orderEvent.setEventType(PROCESS_ORDER_EVENT);
        orderEvent.setPrice(order.getPrice());
        orderEvent.setQuantity(order.getQuantity());
        orderEvent.setStockName(order.getStockName());
        orderEvent.setExchangeName(order.getWalletExchangeMap().getExchange().getExchangeName());
        orderEvent.setTradeType(order.getTradeType().getValue());
        orderEvent.setRecurring(order.isRecurring());
        orderEvent.setWalletId(order.getWalletExchangeMap().getWallet().getWalletId());
        orderEvent.setOrderId(order.getOrderId());
        orderEvent.setOrderGroupId(order.getOrderGroup().getOrderGroupTuid());
        return orderEvent;
    }

}
