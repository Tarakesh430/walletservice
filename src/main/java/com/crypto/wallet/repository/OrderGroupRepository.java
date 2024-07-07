package com.crypto.wallet.repository;

import com.crypto.wallet.entity.order.OrderGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderGroupRepository extends JpaRepository<OrderGroup,String> {
}
