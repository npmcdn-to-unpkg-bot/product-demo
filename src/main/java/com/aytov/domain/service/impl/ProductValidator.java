package com.aytov.domain.service.impl;

import com.aytov.domain.DomainException;
import com.aytov.domain.model.Money;
import com.aytov.domain.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProductValidator {
    public void validate(Product product) {

        if (StringUtils.isEmpty(product.getName())) {
            throw new DomainException(DomainException.Type.InvalidProductName);
        }

        if (product.getPrice().equals(Money.ZERO)) {
            throw new DomainException(DomainException.Type.InvalidProductPrice);
        }

        if(product.getTags() != null && !product.getTags().isEmpty()){
            product.getTags().forEach(productTag -> {
                if (StringUtils.isEmpty(productTag.getName())) {
                    throw new DomainException(DomainException.Type.InvalidProductTagName);
                }
            });
        }
    }
}
