package com.crypto.wallet.repository;

import com.crypto.wallet.entity.Wallet;
import com.crypto.wallet.entity.WalletExchangeKey;
import com.crypto.wallet.entity.WalletExchangeMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletExchangeMapRepository extends JpaRepository<WalletExchangeMap, WalletExchangeKey> {

    @Query("SELECT wem FROM WalletExchangeMap wem WHERE wem.wallet.walletId = :walletId AND wem.isOnboarded = :isOnboarded")
    List<WalletExchangeMap> findByWalletIdAndIsOnboarded(@Param("walletId") String walletId, @Param("isOnboarded") boolean isOnboarded);

    @Query("SELECT wem FROM WalletExchangeMap wem JOIN wem.orders o WHERE o.orderId = :orderId")
    Optional<WalletExchangeMap> findWalletExchangeMapByOrderId(@Param("orderId") String orderId);

}
