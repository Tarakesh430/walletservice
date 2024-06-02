package com.crypto.wallet.repository;

import com.crypto.wallet.entity.WalletExchangeKey;
import com.crypto.wallet.entity.WalletExchangeMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletExchangeMapRepository extends JpaRepository<WalletExchangeMap, WalletExchangeKey> {

}
