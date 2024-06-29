package com.crypto.wallet.controller;

import com.crypto.wallet.entity.order.Order;
import com.crypto.wallet.request.OrderRequest;
import com.crypto.wallet.response.ApiResponse;
import com.crypto.wallet.response.OrderResponse;
import com.crypto.wallet.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor
public class OrderResource {
    private final Logger logger = LoggerFactory.getLogger(OrderResource.class);
    private final OrderService orderService;

    @PostMapping("/place-order")
    public ResponseEntity<ApiResponse<OrderResponse>> placeOrder(@RequestBody @Valid OrderRequest orderRequest) throws Exception {
        logger.info("POST :: PLACE ORDER :: EXECUTE");
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Order Placed Successfully",
                orderService.placeOrder(orderRequest)));
    }
}
