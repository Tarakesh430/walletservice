package com.crypto.wallet.response;

import com.common.library.enums.TradeType;
import com.crypto.wallet.entity.WalletExchangeMap;
import com.crypto.wallet.entity.order.OrderGroup;
import com.crypto.wallet.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderResponse implements Serializable {

    private String orderId;
    private String orderGroupId;
    private boolean orderGroupActive;
    private TradeType tradeType;
    private String stockName;
    private String walletId;
    private String exchangeName;
    private OrderStatus orderStatus;
    private double quantity;
    private double price;
    private boolean isRecurring = false;
    private double executedPrice;
    private double executedQuantity;
    private long createTime;
    private long updateTime;
}
