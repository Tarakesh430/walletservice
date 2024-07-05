package com.crypto.wallet.controller;

import com.common.library.response.ApiResponse;
import com.crypto.wallet.request.OrderRequest;
import com.crypto.wallet.response.OrderResponse;
import com.crypto.wallet.service.OrderService;
import com.github.fge.jsonpatch.JsonPatch;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{order-id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderDetails(@PathVariable("order-id") String globalOrderId) throws Exception {
        logger.info("GET :: GET ORDER :: DETAILS");
        return ResponseEntity.ok(ApiResponse.success("Order Details Retrieved Successfully",
                orderService.getOrderDetails(globalOrderId)));
    }

    @PatchMapping("/{order-id}/update")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderDetails(@PathVariable("order-id") String globalOrderId,
                                                                        List<JsonPatch> jsonPatches) throws Exception {
        logger.info("PATCH :: PATCH ORDER DETAILS :: UPDATE STATUS");
        return ResponseEntity.ok(ApiResponse.success("Order-Status Updated Successfully",
                orderService.applyUpdatesToOrder(globalOrderId,jsonPatches)));

    }

}
