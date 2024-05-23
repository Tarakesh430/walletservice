package com.crypto.wallet.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetails {
    @JsonProperty("email_id")
    private String emailId;
    @JsonProperty("is_limited")
    private boolean isLimited;
    @JsonProperty("create_time")
    private long createTime;
    @JsonProperty("update_time")
    private long updateTime;
    @JsonProperty("wallet_id")
    private String walletId;

}
