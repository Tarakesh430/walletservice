package com.crypto.wallet.response;

import com.crypto.wallet.enums.OrderStatus;
import com.crypto.wallet.enums.TradeType;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderResponse implements Serializable {

    private String orderId;
    private TradeType tradeType;
    private String stockName;
    private OrderStatus orderStatus;
    private double quantity;
    private double price;
    private boolean isRecurring = false;
    private long createTime;
    private long updateTime;
}
