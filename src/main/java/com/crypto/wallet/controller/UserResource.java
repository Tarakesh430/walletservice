package com.crypto.wallet.controller;

import com.common.library.response.ApiResponse;
import com.crypto.wallet.request.CreateUserRequest;
import com.crypto.wallet.response.UserDetails;
import com.crypto.wallet.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserResource {
    private final Logger logger = LoggerFactory.getLogger(UserResource.class);
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserDetails>> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) throws Exception {
        logger.info("NEW:: CREATE USER:: NEW WALLET");
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Successfully Created User", userService.createUser(createUserRequest)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetails> getUser(@PathVariable("userId") String userId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId));
    }

}
