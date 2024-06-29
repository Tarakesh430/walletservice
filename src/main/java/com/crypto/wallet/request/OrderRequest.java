package com.crypto.wallet.request;

import com.crypto.wallet.enums.TradeType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRequest implements Serializable {
    @NotBlank(message = "exchangeName is Mandatory")
    private String exchangeName;
    @NotBlank(message = "walletId is Mandatory")
    private String walletId;
    private String userId;
    @NotNull(message = "TradeType should not be null")
    private TradeType tradeType;
    @NotBlank(message = "Stock Name should not be null")
    private String stockName;
    private double quantity;
    private double price;
    private boolean isRecurring = false;
}
