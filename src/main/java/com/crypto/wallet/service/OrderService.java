package com.crypto.wallet.service;

import com.crypto.wallet.entity.Exchange;
import com.crypto.wallet.entity.Wallet;
import com.crypto.wallet.entity.WalletExchangeMap;
import com.crypto.wallet.entity.order.Order;
import com.crypto.wallet.helper.ExchangeHelper;
import com.crypto.wallet.helper.OrderHelper;
import com.crypto.wallet.helper.WalletHelper;
import com.crypto.wallet.mapper.OrderMapper;
import com.crypto.wallet.repository.OrderRepository;
import com.crypto.wallet.repository.WalletExchangeMapRepository;
import com.crypto.wallet.request.OrderRequest;
import com.crypto.wallet.response.OrderResponse;
import com.crypto.wallet.validations.RequestValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final WalletHelper walletHelper;
    private final ExchangeHelper exchangeHelper;
    private final OrderHelper orderHelper;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;


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
}
