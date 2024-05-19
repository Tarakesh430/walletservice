package com.crypto.wallet.controller;

import com.crypto.wallet.request.CreateUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
public class WalletResource {

    @PostMapping("/")
    public ResponseEntity createUser(@RequestBody CreateUserRequest createUserRequest) {
        return null;
    }
}
