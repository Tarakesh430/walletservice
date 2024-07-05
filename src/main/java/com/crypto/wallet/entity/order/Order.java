package com.crypto.wallet.entity.order;


import com.common.library.enums.TradeType;
import com.crypto.wallet.entity.WalletExchangeMap;
import com.crypto.wallet.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "orders")
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;
    @Column(name = "trade_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TradeType tradeType;
    private String stockName;
    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private double quantity;
    private double price;
    private boolean isRecurring = false;
    private double executedPrice;
    private double executedQuantity;
    @Column(name = "create_time", nullable = false)
    private long createTime;
    @Column(name = "update_time", nullable = false)
    private long updateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "wallet_id", referencedColumnName = "wallet_id"),
            @JoinColumn(name = "exchange_id", referencedColumnName = "exchange_id")
    })
    private WalletExchangeMap walletExchangeMap;
}
