package com.aytov.domain.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MoneyConverter implements AttributeConverter<Money, String> {

    @Override
    public String convertToDatabaseColumn(Money monetaryAmount) {
       return monetaryAmount.toString();
    }

    @Override
    public Money convertToEntityAttribute(String moneyStr) {
        return Money.parse(moneyStr);
    }
}
