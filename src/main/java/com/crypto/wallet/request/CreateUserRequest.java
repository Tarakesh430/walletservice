package com.crypto.wallet.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUserRequest implements Serializable {
    @JsonProperty("email_id")
    @NotEmpty
    private String emailId;
    @JsonProperty("password")
    @NotEmpty
    private String password;
}
