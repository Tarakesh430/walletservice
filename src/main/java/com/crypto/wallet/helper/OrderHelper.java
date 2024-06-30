package com.crypto.wallet.helper;

import com.common.library.utils.CommonUtils;
import com.crypto.wallet.entity.WalletExchangeMap;
import com.crypto.wallet.entity.order.Order;
import com.crypto.wallet.enums.OrderStatus;
import com.crypto.wallet.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderHelper {
    private final Logger logger = LoggerFactory.getLogger(OrderHelper.class);

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
}
