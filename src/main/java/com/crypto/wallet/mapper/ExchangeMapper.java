package com.crypto.wallet.mapper;

import com.crypto.wallet.dto.ExchangeDto;
import com.crypto.wallet.entity.Exchange;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ExchangeMapper {
    ExchangeDto toDto(Exchange exchange);
    Exchange toEntity(ExchangeDto exchangeDto);

    List<ExchangeDto> toDto(List<Exchange> all);
}
