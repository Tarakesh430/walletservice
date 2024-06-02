package com.crypto.wallet.handler;

import com.crypto.wallet.entity.ValidationKey;
import com.crypto.wallet.response.ApiResponse;
import com.crypto.wallet.utils.CommonConstants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Component("coinswitchxKeyValidation")
@RequiredArgsConstructor
public class KeyValidationHandler {
    private final Logger logger = LoggerFactory.getLogger(KeyValidationHandler.class);
    private final RestClient restClient;

    private final String VALIDATION_PATH = "/v1/cryptotrade/validate-keys/";

    @Value("${cryptotrade.trade.api.baseUrl}")
    private String baseUrl;

    public boolean validateKey(String exchangeName, ValidationKey validationKey) {
        logger.info("CoinswitchX EXchange Key Validation being triggered");
        boolean validationStatus = false;
        ResponseEntity<ApiResponse<String>> response = null;
        try {
            response = restClient.post().uri(baseUrl.concat(VALIDATION_PATH).concat(exchangeName))
                    .header(CommonConstants.SECRET_KEY_HEADER, validationKey.getSecretKey())
                    .header(CommonConstants.API_KEY_HEADER, validationKey.getApiKey())
                    .retrieve().body(new ParameterizedTypeReference<>() {
                    });
        } catch (Exception ex) {
            logger.info("Exception ex {} in Validating Keys", ex.getMessage());
        }
        if (Objects.nonNull(response) && response.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("Validated the Validation Keys with response");
            logger.info("Validation Response {}", response.getBody());
            validationStatus = true;
        }
        return validationStatus;
    }

}
