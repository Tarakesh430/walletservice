package com.crypto.wallet.service;

import com.crypto.wallet.dto.WalletExchangeMapDto;
import com.crypto.wallet.entity.*;
import com.crypto.wallet.helper.WalletHelper;
import com.crypto.wallet.handler.KeyValidationHandler;
import com.crypto.wallet.mapper.WalletExchangeMapper;
import com.crypto.wallet.repository.ExchangeRepository;
import com.crypto.wallet.repository.ValidationKeyRepository;
import com.crypto.wallet.repository.WalletExchangeMapRepository;
import com.crypto.wallet.repository.WalletRepository;
import com.crypto.wallet.request.ValidationKeyRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final Logger logger = LoggerFactory.getLogger(WalletService.class);
    private final ExchangeRepository exchangeRepository;
    private final WalletRepository walletRepository;
    private final WalletExchangeMapRepository walletExchangeRepository;
    private final WalletExchangeMapper walletExchangeMapper;
    private final WalletHelper walletHelper;
    private final ValidationKeyRepository validationKeyRepository;
    private final KeyValidationHandler keyValidationHandler;

    @Transactional(value = Transactional.TxType.REQUIRED)
    public WalletExchangeMapDto addExchangeToWallet(String exchangeName, String walletId) throws Exception {
        logger.info("Adding Exchange {} to wallet {}", exchangeName, walletId);
        Exchange exchange = exchangeRepository.findByExchangeName(exchangeName)
                .orElseThrow(() -> new Exception("Exchange not Active or not found.Pls try another"));
        Wallet wallet = walletRepository.findByWalletId(walletId).orElseThrow(() -> new Exception("Wallet not Found"));

        WalletExchangeMap walletExchangeMap = walletHelper.getWalletExchangeMap(exchange, wallet);
        walletExchangeMap = walletExchangeRepository.save(walletExchangeMap);
        return walletExchangeMapper.toDto(walletExchangeMap);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public void updateValidationKeys(ValidationKeyRequest request, String exchange, String walletId) throws Exception {
        logger.info("Updating the Key Validation Object");
        logger.info("Get Exchange Details");
        Exchange exchangeDetails = exchangeRepository.findByExchangeName(exchange)
                .orElseThrow(() -> new Exception("Invalid Exchange Name provided"));
        logger.info("Exchange Details Present");
        WalletExchangeMap walletExchangeMap = walletHelper.getWalletExchangeMap(walletId, exchangeDetails.getExchangeId());
        ValidationKey finalKey = walletExchangeMap.getValidationKey();
        finalKey.setApiKey(request.getApiKey());
        finalKey.setSecretKey(request.getSecretKey());
        finalKey.setUpdateTime(System.currentTimeMillis());
        finalKey.setValid(false);
        validationKeyRepository.save(finalKey);
        logger.info("Updated the Key Validations Successfully");
    }


    public void validateKeys(String exchangeName, String walletId) throws Exception {
        logger.info("Validate Keys");
        Exchange exchangeDetails = exchangeRepository.findByExchangeName(exchangeName)
                .orElseThrow(() -> new Exception("Invalid Exchange Name provided"));
        logger.info("Exchange Details Present");
        WalletExchangeMap walletExchangeMap = walletHelper.getWalletExchangeMap(walletId, exchangeDetails.getExchangeId());
        ValidationKey finalKey = walletExchangeMap.getValidationKey();
        keyValidationHandler.validateKey(exchangeName, finalKey);

    }
}
