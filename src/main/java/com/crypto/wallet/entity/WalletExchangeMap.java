package com.crypto.wallet.entity;

import com.crypto.wallet.entity.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "wallet_exchange_map")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletExchangeMap {
    @EmbeddedId
    WalletExchangeKey key;

    @Column(name = "is_onboarded", nullable = false)
    private boolean isOnboarded;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "validation_id", referencedColumnName = "validation_key_uuid")
    public ValidationKey validationKey;

    @Column(name = "create_time", nullable = false)
    private long createTime;
    @Column(name = "update_time", nullable = false)
    private long updateTime;

    @ManyToOne(fetch=FetchType.EAGER)
    @MapsId("walletId")
    @JoinColumn(name = "wallet_id")
    Wallet wallet;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("exchangeId")
    @JoinColumn(name = "exchange_id")
    Exchange exchange;

    @OneToMany(mappedBy = "walletExchangeMap", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Order> orders;
}
