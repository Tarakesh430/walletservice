package com.crypto.wallet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@Entity
@Table(name = "exchange")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "exchange_id", unique = true, nullable = false)
    private String exchangeId;

    @Column(name = "exchange_name", unique = true, nullable = false)
    private String exchangeName;
    @Column(name = "created_by", nullable = false)
    private String createdBy;
    @Column(name = "is_allowed", nullable = false)
    private boolean isAllowed;
    @Column(name = "create_time", nullable = false)
    private long createTime;
    @Column(name = "update_time", nullable = false)
    private long updateTime;

    @OneToMany(mappedBy = "exchange")
    private Set<WalletExchangeMap> walletExchangeMap;
}
