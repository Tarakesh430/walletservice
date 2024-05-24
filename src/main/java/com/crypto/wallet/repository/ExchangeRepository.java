package com.crypto.wallet.repository;

import com.crypto.wallet.entity.Exchange;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRepository  extends JpaRepository<Exchange,String> {
    Optional<Exchange> findByExchangeName(String exchange);
    List<Exchange> findAll(Specification<Exchange> specs);
}
