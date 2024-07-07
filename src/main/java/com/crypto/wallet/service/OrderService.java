package com.crypto.wallet.service;

import com.common.library.utils.CommonUtils;
import com.crypto.wallet.entity.Exchange;
import com.crypto.wallet.entity.WalletExchangeMap;
import com.crypto.wallet.entity.order.Order;
import com.crypto.wallet.entity.order.OrderGroup;
import com.crypto.wallet.enums.OrderStatus;
import com.crypto.wallet.helper.ExchangeHelper;
import com.crypto.wallet.helper.OrderHelper;
import com.crypto.wallet.helper.WalletHelper;
import com.crypto.wallet.mapper.OrderMapper;
import com.crypto.wallet.repository.OrderGroupRepository;
import com.crypto.wallet.repository.OrderRepository;
import com.crypto.wallet.repository.WalletExchangeMapRepository;
import com.crypto.wallet.request.OrderRequest;
import com.crypto.wallet.response.OrderResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;


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
    private final OrderGroupRepository orderGroupRepository;



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
        OrderGroup orderGroup = orderHelper.getOrCreateNewOrderGroup(orderRequest.getOrderGroupId());
        if(!orderGroup.isActive()){
            throw new Exception("Order Group not active further transactions not allowed in this group");
        }
        Order order = orderHelper.createOrder(orderRequest, walletExchangeMap);
        order.setOrderGroup(orderGroup);
        order = orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    @Transactional
    public OrderResponse getOrderDetails(String globalOrderId) throws Exception {
        logger.info("Get Order Details for Order Id");
        Order order = orderRepository.findById(globalOrderId)
                .orElseThrow(() -> new Exception("Invalid Order Id"));
        OrderGroup orderGroup = order.getOrderGroup();
        return  prepareOrderResponse(order,orderGroup);
    }

    private OrderResponse prepareOrderResponse(Order order, OrderGroup orderGroup) throws Exception {
        OrderResponse orderResponse =orderMapper.toResponse(order);
        WalletExchangeMap walletExchangeMap = walletExchangeMapRepository
                .findWalletExchangeMapByOrderId(order.getOrderId())
                .orElse(null);
        if(Objects.nonNull(walletExchangeMap)){
            String exchangeName = exchangeHelper
                    .getActiveExchangeByExchangeId(walletExchangeMap.getKey().getExchangeId()).getExchangeName();
            String walletId = walletExchangeMap.getKey().getWalletId();
            orderResponse.setWalletId(walletId);
            orderResponse.setExchangeName(exchangeName);
        }
        orderResponse.setOrderGroupId(orderGroup.getOrderGroupTuid());
        orderResponse.setOrderGroupActive(orderGroup.isActive());
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
        JsonNode patchedOrderNode = CommonUtils.applyJsonPatches(jsonPatches, oldOrderNode);
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
        return prepareOrderResponse(savedOrder, savedOrder.getOrderGroup());
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

    @Transactional
    public void deactivateOrderGroupId(String orderGroupId) throws Exception {
        logger.info("Deactivating the Order Group Id");
        OrderGroup orderGroup = orderGroupRepository.findById(orderGroupId).orElseThrow(() -> new Exception("Order Group Info Not found"));
        orderGroup.setActive(false);
        orderGroup.setUpdateTime(CommonUtils.getEpochTimeStamp());
        orderGroupRepository.save(orderGroup);
    }


    public List<OrderResponse> getOrderDetailsForOrderGroupId(String orderGroupId) throws Exception {
        logger.info(" Get Order Details for the Order Group Id");
        OrderGroup orderGroup = orderGroupRepository.findById(orderGroupId).orElseThrow(() -> new Exception("Order Group Info Not found"));
        List<Order> orders = orderGroup.getOrders();
        List<OrderResponse> orderResponseList = new ArrayList<>();
        for (Order order : orders) {
            orderResponseList.add(prepareOrderResponse(order,orderGroup));
        }
        orderResponseList.sort(Comparator.comparingLong(OrderResponse::getCreateTime));
        return orderResponseList;
    }
}
