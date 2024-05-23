package com.crypto.wallet.service;

import com.crypto.wallet.entity.Wallet;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final Logger logger = LoggerFactory.getLogger(WalletService.class);
}
