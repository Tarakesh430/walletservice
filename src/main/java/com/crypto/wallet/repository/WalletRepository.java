package com.crypto.wallet.repository;

import com.crypto.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,String> {
    Optional<Wallet> findByWalletId(String walletId);
}
