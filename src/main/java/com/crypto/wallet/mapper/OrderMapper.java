package com.crypto.wallet.mapper;

import com.crypto.wallet.entity.order.Order;
import com.crypto.wallet.request.OrderRequest;
import com.crypto.wallet.response.OrderResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface OrderMapper {
    Order toEntity(OrderRequest orderRequest);
    OrderResponse toResponse(Order order);
}
