package com.crypto.wallet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "wallet")
@Data
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "wallet_id", unique = true, nullable = false)
    private String walletId;
    @Column(name = "total_balance", nullable = false)
    private double totalBalance;
    @Column(name = "create_time", nullable = false)
    private long createTime;
    @Column(name = "update_time", nullable = false)
    private long updateTime;
    @OneToOne(mappedBy = "wallet")
    private User user;

    @OneToMany(mappedBy = "wallet")
    private Set<WalletExchangeMap> walletExchangeMap;
}