package com.crypto.wallet.controller;

import com.crypto.wallet.service.WalletService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/wallet")
@RequiredArgsConstructor
public class WalletResource {

    private final Logger logger= LoggerFactory.getLogger(WalletResource.class);
    private final WalletService walletService;
    @PostMapping("/{walletId}/exchange/{exchangeName}")
    public ResponseEntity onboardExchangeToWallet(@PathParam("walletId") String walletId,
                                                  @PathParam("exchangeName") String exchangeName){
        logger.info("Onboard exchange {} to wallet {}",exchangeName,walletId);

        return null;
    }




}
