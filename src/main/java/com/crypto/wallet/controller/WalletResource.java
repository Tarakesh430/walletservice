package com.crypto.wallet.controller;


import com.crypto.wallet.dto.ValidationKeyDto;
import com.crypto.wallet.dto.WalletExchangeMapDto;
import com.crypto.wallet.request.ValidationKeyRequest;
import com.crypto.wallet.response.ApiResponse;
import com.crypto.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/wallet")
@RequiredArgsConstructor
public class WalletResource {

    private final Logger logger = LoggerFactory.getLogger(WalletResource.class);
    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<ApiResponse<WalletExchangeMapDto>> onboardExchangeToWallet(@RequestBody WalletExchangeMapDto walletExchangeMapDto) throws Exception {
        logger.info("Onboard exchange {} to wallet {}", walletExchangeMapDto.getExchangeName(),
                walletExchangeMapDto.getWalletId());
        return ResponseEntity.ok(ApiResponse.success("Wallet Exchange Onboarded",
                walletService.addExchangeToWallet(walletExchangeMapDto.getExchangeName(),
                        walletExchangeMapDto.getWalletId())));
    }


    @PostMapping("/{walletId}/exchange/{exchangeName}")
    public ResponseEntity<ApiResponse<String>> updateValidationKeys(@RequestBody @Validated ValidationKeyRequest validationKeyRequest,
                                                                    @PathVariable("walletId") String walletId,
                                                                    @PathVariable("exchangeName") String exchangeName) throws Exception {
        logger.info("POST:: UPDATE KEY VALIDATIONS");
        walletService.updateValidationKeys(validationKeyRequest, exchangeName, walletId);
        return ResponseEntity.ok(ApiResponse.success("VALIDATION KEYS UPDATED SUCCESSFULLY", null));
    }

    @PostMapping("/{walletId}/exchange/{exchangeName}/validate")
    public ResponseEntity<ApiResponse<String>> validateKeys(@PathVariable("walletId") String walletId,
                                                            @PathVariable("exchangeName") String exchangeName) throws Exception {
        logger.info("POST:: UPDATE KEY VALIDATIONS");
        walletService.validateKeys( exchangeName, walletId);
        return ResponseEntity.ok(ApiResponse.success("VALIDATION KEYS UPDATED SUCCESSFULLY", null));
    }

}
