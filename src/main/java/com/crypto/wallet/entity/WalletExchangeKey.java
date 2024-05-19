package com.crypto.wallet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class WalletExchangeKey implements Serializable {

    @Column(name = "wallet_id")
    String walletId;

    @Column(name = "exchange_id")
    String exchangeId;
}
