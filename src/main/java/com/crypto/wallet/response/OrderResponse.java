package com.crypto.wallet.response;

import com.common.library.enums.TradeType;
import com.crypto.wallet.enums.OrderStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderResponse implements Serializable {

    private String orderId;
    private TradeType tradeType;
    private String stockName;
    private String walletId;
    private String exchangeName;
    private OrderStatus orderStatus;
    private double quantity;
    private double price;
    private boolean isRecurring = false;
    private long createTime;
    private long updateTime;
}
