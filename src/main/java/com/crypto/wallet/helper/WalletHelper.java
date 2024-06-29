package com.crypto.wallet.helper;

import com.crypto.wallet.entity.*;
import com.crypto.wallet.repository.ExchangeRepository;
import com.crypto.wallet.repository.WalletExchangeMapRepository;
import com.crypto.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WalletHelper {
    private final Logger logger = LoggerFactory.getLogger(WalletHelper.class);
    private final WalletExchangeMapRepository walletExchangeRepository;
    private final WalletRepository walletRepository;

    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        wallet.setCreateTime(System.currentTimeMillis());
        wallet.setUpdateTime(System.currentTimeMillis());
        wallet.setTotalBalance(0);
        return wallet;
    }

    public WalletExchangeMap getWalletExchangeMap(Exchange exchange, Wallet wallet) {
        WalletExchangeMap walletExchangeMap = new WalletExchangeMap();

        WalletExchangeKey walletExchangeKey = new WalletExchangeKey();
        walletExchangeMap.setExchange(exchange);
        walletExchangeMap.setWallet(wallet);
        walletExchangeMap.setKey(walletExchangeKey);
        walletExchangeMap.setCreateTime(System.currentTimeMillis());
        walletExchangeMap.setUpdateTime(System.currentTimeMillis());
        walletExchangeMap.setOnboarded(true);

        ValidationKey validationKey = getValidationKey(StringUtils.EMPTY, StringUtils.EMPTY);
        walletExchangeMap.setValidationKey(validationKey);
        return walletExchangeMap;
    }

    public ValidationKey getValidationKey(String apiKey, String secretKey) {
        ValidationKey validationKey = new ValidationKey();
        validationKey.setApiKey(apiKey);
        validationKey.setSecretKey(secretKey);
        validationKey.setCreateTime(System.currentTimeMillis());
        validationKey.setUpdateTime(System.currentTimeMillis());
        validationKey.setValid(false);
        return validationKey;
    }

    public WalletExchangeMap getWalletExchangeMap(String walletId, String exchangeId) throws Exception {
        WalletExchangeKey walletExchangeKey = new WalletExchangeKey();
        walletExchangeKey.setWalletId(walletId);
        walletExchangeKey.setExchangeId(exchangeId);
        WalletExchangeMap walletExchangeMap = walletExchangeRepository.findById(walletExchangeKey)
                .orElseThrow(() -> new Exception("Exchange not onboarded to the wallet Provided"));
        logger.info("the Wallet {} opted for Exchange {}",walletId,exchangeId);
        return walletExchangeMap;
    }

    public Wallet getWalletDetails(String walletId) throws Exception {
        return walletRepository.findByWalletId(walletId).orElseThrow(()-> new Exception("Wallet Details Not Found"));
    }


}
