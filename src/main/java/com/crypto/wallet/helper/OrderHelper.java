package com.crypto.wallet.helper;

import com.common.library.utils.CommonUtils;
import com.crypto.wallet.entity.WalletExchangeMap;
import com.crypto.wallet.entity.order.Order;
import com.crypto.wallet.entity.order.OrderGroup;
import com.crypto.wallet.enums.OrderStatus;
import com.crypto.wallet.repository.OrderGroupRepository;
import com.crypto.wallet.request.OrderRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderHelper {
    private final Logger logger = LoggerFactory.getLogger(OrderHelper.class);
    private final OrderGroupRepository orderGroupRepository;

    public Order createOrder(OrderRequest orderRequest, WalletExchangeMap walletExchangeMap){
        Order newOrder = new Order();
        newOrder.setOrderStatus(OrderStatus.CREATED);
        newOrder.setPrice(orderRequest.getPrice());
        newOrder.setRecurring(orderRequest.isRecurring());
        newOrder.setStockName(orderRequest.getStockName());
        newOrder.setQuantity(orderRequest.getQuantity());
        newOrder.setTradeType(orderRequest.getTradeType());
        newOrder.setWalletExchangeMap(walletExchangeMap);
        newOrder.setCreateTime(CommonUtils.getEpochTimeStamp());
        newOrder.setUpdateTime(CommonUtils.getEpochTimeStamp());
        return newOrder;
    }

    @Transactional
    public OrderGroup getOrCreateNewOrderGroup(String orderGroupId) {
        logger.info("Get Or Create new OrderGroup Id");
        Optional<OrderGroup> orderGroup = orderGroupRepository.findById(orderGroupId);
        if(orderGroup.isPresent()){
            logger.info("order Group already Exists");
            return orderGroup.get();
        }
        OrderGroup newOrderGroup = new OrderGroup();
        newOrderGroup.setCreateTime(CommonUtils.getEpochTimeStamp());
        newOrderGroup.setUpdateTime(CommonUtils.getEpochTimeStamp());
        newOrderGroup.setActive(true);
        OrderGroup savedOrderGroup = orderGroupRepository.save(newOrderGroup);
        logger.info("Newly Created Order Group {}",savedOrderGroup);
        return savedOrderGroup;
    }
}
