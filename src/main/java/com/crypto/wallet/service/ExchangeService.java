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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final Logger logger = LoggerFactory.getLogger(ExchangeService.class);
    private final ExchangeRepository exchangeRepository;
    private final ExchangeMapper exchangeMapper;

    public ExchangeDto getExchangeDetails(String exchange) throws Exception {
        logger.info("ExchangeService :: Get Exchange info :: {}", exchange);
        Optional<Exchange> byExchangeName = exchangeRepository.findByExchangeName(exchange);
        logger.info("{}",byExchangeName.isPresent());
        return byExchangeName
                .map(exchangeMapper::toDto).orElseThrow(()->new Exception("Exception in getting exchange details"));

    }

    public List<ExchangeDto> getExchanges(String type, Boolean isActive){
        logger.info("Get Exchanges with filters");
        Specification<Exchange> spec = Specification.where(ExchangeSpecification.hasActive(isActive))
                .and(ExchangeSpecification.hasType(Type.fromString(type)));
        return exchangeMapper.toDto(exchangeRepository.findAll(spec));
    }

    public ExchangeDto saveExchange(ExchangeDto exchangeDto) {
        logger.info("Saving an exchange");
        Exchange exchange = exchangeMapper.toEntity(exchangeDto);
        exchange.setCreateTime(System.currentTimeMillis());
        exchange.setUpdateTime(System.currentTimeMillis());
        exchange.setActive(true);
        exchange = exchangeRepository.save(exchange);
        return exchangeMapper.toDto(exchange);
    }
}
