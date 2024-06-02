package com.crypto.wallet.dto;

import com.crypto.wallet.enums.Type;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExchangeDto {
    @JsonProperty("exchange_id")
    private String exchangeId;
    @JsonProperty("exchange_name")
    private String exchangeName;
    @JsonProperty("type")
    private Type type;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("is_active")
    private boolean isActive;
    @JsonProperty("create_time")
    private long createTime;
    @JsonProperty("update_time")
    private long updateTime;
}
