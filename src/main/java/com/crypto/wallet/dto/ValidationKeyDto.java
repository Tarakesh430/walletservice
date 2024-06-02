package com.crypto.wallet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ValidationKeyDto{
    @JsonProperty("api_key")
    private String apiKey;
    @JsonProperty("secret_key")
    private String secretKey;
    @JsonProperty( "is_valid")
    private boolean isValid;
    @JsonProperty("last_validated_time")
    private long lastValidatedTime;
    @JsonProperty("create_time")
    private long createTime;
    @JsonProperty("update_time")
    private long updateTime;
}
