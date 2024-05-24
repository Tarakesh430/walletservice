package com.crypto.wallet.service;

import com.crypto.wallet.dto.ExchangeDto;
import com.crypto.wallet.entity.Wallet;
import com.crypto.wallet.entity.WalletExchangeKey;
import com.crypto.wallet.entity.WalletExchangeMap;
import com.crypto.wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final Logger logger = LoggerFactory.getLogger(WalletService.class);
    private final ExchangeService exchangeService;
    private final WalletRepository walletRepository;
    @Transactional
    public void addExchangeToWallet(String exchangeName,String walletId) throws Exception {
        logger.info("Adding Exchange {} to wallet {}",exchangeName,walletId);
        ExchangeDto exchangeDetails = exchangeService.getExchangeDetails(exchangeName);
        if(!exchangeDetails.isActive()){
            throw new Exception("Exchange not Active or not found.Pls try another");
        }
        Optional<Wallet> wallet = walletRepository.findByWalletId(walletId);
        if(wallet.isEmpty()){
            throw new Exception("Wallet not Found");
        }
        WalletExchangeMap walletExchangeMap =new WalletExchangeMap();

        WalletExchangeKey walletExchangeKey = new WalletExchangeKey();
        walletExchangeKey.setExchangeId(exchangeDetails.getExchangeId());
        walletExchangeKey.setWalletId(walletId);

        walletExchangeMap.setKey(walletExchangeKey);
        walletExchangeMap.setCreateTime(System.currentTimeMillis());
        walletExchangeMap.setUpdateTime(System.currentTimeMillis());

        walletExchangeMap.setOnboarded(true);





    }
}
