package com.crypto.wallet.validations;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestValidator {
    private final Logger logger = LoggerFactory.getLogger(RequestValidator.class);

}
