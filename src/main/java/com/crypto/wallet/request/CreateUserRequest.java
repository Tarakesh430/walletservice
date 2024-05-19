package com.crypto.wallet.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUserRequest implements Serializable {
    private String email;
    private String password;
}
