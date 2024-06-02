package com.crypto.wallet.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationKeyRequest implements Serializable {
    @JsonProperty("api_key")
    @NotBlank
    private String apiKey;
    @NotBlank
    @JsonProperty("secret_key")
    private String secretKey;
}
