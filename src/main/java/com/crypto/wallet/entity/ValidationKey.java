package com.crypto.wallet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "validation_key")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationKey {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "validation_key_uuid", unique = true, nullable = false)
    private String validationKeyUuid;

    @Column(name = "api_key")
    private String apiKey;

    @Column(name = "secret_key")
    private String secretKey;
    @Column(name = "is_valid", nullable = false)
    private boolean isValid;
    @Column(name = "last_validated_time", nullable = false)
    private long lastValidatedTime;
    @Column(name = "create_time", nullable = false)
    private long createTime;
    @Column(name = "update_time", nullable = false)
    private long updateTime;

    @OneToOne(mappedBy = "validationKey")
    private WalletExchangeMap walletExchangeMap;
}
