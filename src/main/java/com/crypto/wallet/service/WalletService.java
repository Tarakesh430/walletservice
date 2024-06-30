package com.crypto.wallet.service;

import com.crypto.wallet.dto.ValidationKeyDto;
import com.crypto.wallet.dto.WalletExchangeMapDto;
import com.crypto.wallet.entity.*;
import com.crypto.wallet.helper.ExchangeHelper;
import com.crypto.wallet.helper.WalletHelper;
import com.crypto.wallet.handler.KeyValidationHandler;
import com.crypto.wallet.mapper.ValidationKeyMapper;
import com.crypto.wallet.mapper.WalletExchangeMapper;
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
    private final WalletRepository walletRepository;
    private final WalletExchangeMapRepository walletExchangeRepository;
    private final WalletExchangeMapper walletExchangeMapper;
    private final WalletHelper walletHelper;
    private final ValidationKeyRepository validationKeyRepository;
    private final KeyValidationHandler keyValidationHandler;
    private final ExchangeHelper exchangeHelper;
    private final ValidationKeyMapper validationKeyMapper;

    @Transactional(value = Transactional.TxType.REQUIRED)
    public WalletExchangeMapDto addExchangeToWallet(String exchangeName, String walletId) throws Exception {
        logger.info("Adding Exchange {} to wallet {}", exchangeName, walletId);
        Exchange exchange = exchangeHelper.getActiveExchange(exchangeName);
        Wallet wallet = walletRepository.findByWalletId(walletId).orElseThrow(() -> new Exception("Wallet not Found"));

        WalletExchangeMap walletExchangeMap = walletHelper.getWalletExchangeMap(exchange, wallet);
        walletExchangeMap = walletExchangeRepository.save(walletExchangeMap);
        return walletExchangeMapper.toDto(walletExchangeMap);
    }


    @Transactional(value = Transactional.TxType.REQUIRED)
    public void updateValidationKeys(ValidationKeyRequest request, String exchangeName, String walletId) throws Exception {
        logger.info("Updating the Key Validation Object");
        logger.info("Get Exchange Details");
        Exchange exchangeDetails = exchangeHelper.getActiveExchange(exchangeName);
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


    @Transactional
    public void validateKeys(String exchangeName, String walletId) throws Exception {
        logger.info("Validate Keys");
        Exchange exchangeDetails = exchangeHelper.getActiveExchange(exchangeName);
        logger.info("Exchange Details Present");
        WalletExchangeMap walletExchangeMap = walletHelper.getWalletExchangeMap(walletId, exchangeDetails.getExchangeId());
        ValidationKey finalKey = walletExchangeMap.getValidationKey();
        boolean keyValidationStatus = keyValidationHandler.validateKey(exchangeName, finalKey);
        if (keyValidationStatus) {
            logger.info("Key Validation Failed for the Exchange {} Wallet Id{}", exchangeName, walletId);
            throw new Exception("Key Validation Failed ");
        }
        finalKey.setValid(keyValidationStatus);
        validationKeyRepository.save(finalKey);
    }

    public ValidationKeyDto getValidationKeys(String exchangeName, String walletId) throws Exception {
        logger.info("Get Validate Keys");
        Exchange exchangeDetails = exchangeHelper.getActiveExchange(exchangeName);
        WalletExchangeMap walletExchangeMap = walletHelper.getWalletExchangeMap(walletId, exchangeDetails.getExchangeId());
        ValidationKey validationKey = walletExchangeMap.getValidationKey();
        return validationKeyMapper.toDto(validationKey);
    }

}
