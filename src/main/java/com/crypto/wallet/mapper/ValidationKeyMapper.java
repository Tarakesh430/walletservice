package com.crypto.wallet.mapper;

import com.crypto.wallet.dto.ValidationKeyDto;
import com.crypto.wallet.entity.ValidationKey;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ValidationKeyMapper {
    ValidationKey toEntity(ValidationKeyDto validationDto);
    ValidationKeyDto toDto(ValidationKey validationKey);
}
