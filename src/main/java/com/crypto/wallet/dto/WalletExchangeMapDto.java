package com.crypto.wallet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WalletExchangeMapDto {

    @JsonProperty("is_onboarded")
    private boolean isOnboarded;

    @JsonProperty("create_time")
    private long createTime;

    @JsonProperty("update_time")
    private long updateTime;

    @JsonProperty("wallet_id")
    private String walletId;

    @JsonProperty("exchange_name")
    private String exchangeName;
}
