package com.crypto.wallet.kafka.event;

import com.crypto.wallet.enums.TradeType;
import lombok.Data;

@Data
public class OrderEvent extends BaseEvent{
    private String exchangeName;
    private String orderId;
    private String walletId;
    private String userId;
    private TradeType tradeType;
    private String stockName;
    private double quantity;
    private double price;
    private boolean isRecurring = false;
}
