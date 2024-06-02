package com.crypto.wallet.mapper;

import com.crypto.wallet.dto.WalletExchangeMapDto;
import com.crypto.wallet.entity.WalletExchangeMap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface WalletExchangeMapper {

    @Mapping(target="walletId",source="walletExchangeMap.key.walletId")
    @Mapping(target="exchangeName",source="walletExchangeMap.key.exchangeId")
    WalletExchangeMapDto toDto(WalletExchangeMap walletExchangeMap);
}
