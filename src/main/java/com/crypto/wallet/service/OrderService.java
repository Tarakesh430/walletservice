package com.crypto.wallet.service;

import com.common.library.utils.CommonUtils;
import com.crypto.wallet.entity.Exchange;
import com.crypto.wallet.entity.WalletExchangeMap;
import com.crypto.wallet.entity.order.Order;
import com.crypto.wallet.enums.OrderStatus;
import com.crypto.wallet.helper.ExchangeHelper;
import com.crypto.wallet.helper.OrderHelper;
import com.crypto.wallet.helper.WalletHelper;
import com.crypto.wallet.mapper.OrderMapper;
import com.crypto.wallet.repository.OrderRepository;
import com.crypto.wallet.repository.WalletExchangeMapRepository;
import com.crypto.wallet.request.OrderRequest;
import com.crypto.wallet.response.OrderResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final WalletHelper walletHelper;
    private final ExchangeHelper exchangeHelper;
    private final OrderHelper orderHelper;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final WalletExchangeMapRepository walletExchangeMapRepository;
    private final ObjectMapper objectMapper;


    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest) throws Exception {
        logger.info("Executing Order");
        //get Wallet Details
        Exchange activeExchange = exchangeHelper.getActiveExchange(orderRequest.getExchangeName());
        WalletExchangeMap walletExchangeMap =
                walletHelper.getWalletExchangeMap(orderRequest.getWalletId(), activeExchange.getExchangeId());
        if (!walletExchangeMap.isOnboarded()) {
            throw new Exception("Exchange not onboarded to the Wallet");
        }
        Order order = orderHelper.createOrder(orderRequest, walletExchangeMap);
        order = orderRepository.save(order);

        return orderMapper.toResponse(order);
    }

    public OrderResponse getOrderDetails(String globalOrderId) throws Exception {
        logger.info("Get Order Details for Order Id");
        Order order = orderRepository.findById(globalOrderId)
                .orElseThrow(() -> new Exception("Invalid Order Id"));
       return  prepareOrderResponse(order);
    }

    private OrderResponse prepareOrderResponse(Order order) throws Exception {
        OrderResponse orderResponse =orderMapper.toResponse(order);
        WalletExchangeMap walletExchangeMap = walletExchangeMapRepository
                .findWalletExchangeMapByOrderId(order.getOrderId())
                .orElseThrow(() -> new Exception("Error in getting wallet Exchange Map Details"));
        String exchangeName = exchangeHelper
                .getActiveExchangeByExchangeId(walletExchangeMap.getKey().getExchangeId()).getExchangeName();
        String walletId = walletExchangeMap.getKey().getWalletId();
        orderResponse.setWalletId(walletId);
        orderResponse.setExchangeName(exchangeName);
        return orderResponse;
    }


    private void validateOrderStatusUpdate(OrderStatus fromStatus,OrderStatus toStatus) throws Exception {
        if (!fromStatus.equals(toStatus) && !OrderStatus.isValidTransition(fromStatus, toStatus) ){
            String errorMessage = String.format("Invalid status transition from %s to %s. Please try again.",
                    fromStatus, toStatus);
            logger.error(errorMessage);
            throw new Exception(errorMessage);
        }
    }

    @Transactional
    public OrderResponse applyUpdatesToOrder(String globalOrderId, List<JsonPatch> jsonPatches) throws Exception {
        logger.info("Apply Json Patch Updates to the Order");
        Order oldOrder = orderRepository.findById(globalOrderId)
                .orElseThrow(() -> new Exception("No Order Details Found"));
        JsonNode oldOrderNode = objectMapper.convertValue(oldOrder, JsonNode.class);
        JsonNode patchedOrderNode = applyJsonPatches(jsonPatches, oldOrderNode);
        Order patchedOrder = objectMapper.treeToValue(patchedOrderNode, Order.class);
        validateOrderUpdate(oldOrder,patchedOrder);
        //Update the older order with the patched data
        oldOrder.setOrderStatus(patchedOrder.getOrderStatus());
        oldOrder.setQuantity(patchedOrder.getQuantity());
        oldOrder.setPrice(patchedOrder.getPrice());
        oldOrder.setRecurring(patchedOrder.isRecurring());
        oldOrder.setExecutedPrice(patchedOrder.getExecutedPrice());
        oldOrder.setExecutedQuantity(patchedOrder.getExecutedQuantity());
        oldOrder.setUpdateTime(CommonUtils.getEpochTimeStamp());
        //Save the updated Order data
        Order savedOrder = orderRepository.save(oldOrder);
        return prepareOrderResponse(savedOrder);
    }

    private void validateOrderUpdate(Order oldOrder, Order patchedOrder) throws Exception {
        validateOrderStatusUpdate(oldOrder.getOrderStatus(),patchedOrder.getOrderStatus());
        if(!oldOrder.getTradeType().equals(patchedOrder.getTradeType())){
            throw new Exception("Trade Type Update Not Allowed");
        }
        if(!oldOrder.getStockName().equals(patchedOrder.getStockName())){
            throw new Exception("Trade Type Update Not Allowed");
        }
        if(!oldOrder.getOrderId().equals(patchedOrder.getOrderId())){
            throw new Exception("Order Id update not allowed");
        }
    }


    private JsonNode applyJsonPatches(List<JsonPatch> jsonPatches, JsonNode jsonNode) throws JsonPatchException {
        for (JsonPatch jsonPatch : jsonPatches) {
            jsonNode = jsonPatch.apply(jsonNode);
        }
        return jsonNode;
    }
}
