package com.crypto.wallet.repository.specifications;

import com.crypto.wallet.entity.Exchange;
import com.crypto.wallet.enums.Type;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

@UtilityClass
public class ExchangeSpecification {

    public static Specification<Exchange> hasType(Type type) {
        return (root, query, criteriaBuilder) ->
                Objects.isNull(type) ? null : criteriaBuilder.equal(root.get("type"), type);
    }

    public static Specification<Exchange> hasActive(Boolean isActive) {
        return (root, query, criteriaBuilder) ->
                Objects.isNull(isActive) ? null : criteriaBuilder.equal(root.get("isActive"), isActive);
    }
}