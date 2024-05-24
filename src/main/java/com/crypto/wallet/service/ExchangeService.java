package com.crypto.wallet.service;

import com.crypto.wallet.dto.ExchangeDto;
import com.crypto.wallet.entity.Exchange;
import com.crypto.wallet.enums.Type;
import com.crypto.wallet.mapper.ExchangeMapper;
import com.crypto.wallet.repository.ExchangeRepository;
import com.crypto.wallet.repository.specifications.ExchangeSpecification;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final Logger logger = LoggerFactory.getLogger(ExchangeService.class);
    private final ExchangeRepository exchangeRepository;
    private final ExchangeMapper exchangeMapper;

    public ExchangeDto getExchangeDetails(String exchange) throws Exception {
        logger.info("ExchangeService :: Get Exchange info :: {}", exchange);
        return exchangeRepository.findByExchangeName(exchange).map(exchangeMapper::toDto)
                .orElseThrow(() -> new Exception("Error in getting Exchange Details"));
    }

    public List<ExchangeDto> getExchanges(String type, Boolean isActive){
        logger.info("Get Exchanges with filters");
        Specification<Exchange> spec = Specification.where(ExchangeSpecification.hasActive(isActive))
                .and(ExchangeSpecification.hasType(Type.fromString(type)));
        return exchangeMapper.toDto(exchangeRepository.findAll(spec));
    }
}
