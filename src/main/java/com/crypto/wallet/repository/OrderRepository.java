package com.crypto.wallet.repository;

import com.crypto.wallet.entity.order.Order;
import com.crypto.wallet.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
    @Query("SELECT o FROM orders o WHERE o.createTime > :time AND o.orderStatus = :status")
    List<Order> findCreatedOrdersByTimeAndStatus(@Param("time") long time, @Param("status") OrderStatus status);

    @Modifying
    @Transactional
    @Query("UPDATE orders o SET o.orderStatus = :toStatus WHERE o.orderId = :orderId and o.orderStatus = :fromStatus")
    void updateOrderStatus(@Param("fromStatus") OrderStatus fromStatus,
                           @Param("toStatus")OrderStatus toStatus, @Param("orderId") String orderId);
}
