package com.crypto.wallet.helper;

import com.crypto.wallet.entity.Wallet;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletHelper {
    private final Logger logger = LoggerFactory.getLogger(WalletHelper.class);
    public Wallet createWallet(){
        Wallet wallet = new Wallet();
        wallet.setCreateTime(System.currentTimeMillis());
        wallet.setUpdateTime(System.currentTimeMillis());
        wallet.setTotalBalance(0);
        return wallet;
    }
}
