package com.crypto.wallet.helper;

import com.crypto.wallet.entity.Exchange;
import com.crypto.wallet.repository.ExchangeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeHelper {
    private final Logger logger = LoggerFactory.getLogger(ExchangeHelper.class);
    private final ExchangeRepository exchangeRepository;
    public Exchange getActiveExchange(String exchangeName) throws Exception {
        logger.info("Get Active Exchange Details {}",exchangeName);
        Exchange exchange = exchangeRepository.findByExchangeName(exchangeName)
                .orElseThrow(() -> new Exception("Exchange not Active or not found.Pls try another"));
        if(!exchange.isActive()){
            logger.info("EXchange is not active. Pls check and try again");
            throw new Exception("Onboarding Exchange is not active. Pls check the Exchange Status And try Again");
        }
        return exchange;
    }
}
