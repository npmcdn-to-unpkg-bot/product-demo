package com.aytov.domain.service;

import com.aytov.domain.model.Money;

import java.util.List;

public interface CurrencyConverter {
    /**
     * Converts @param to a set of predefined currencies.
     * @param price
     * @return
     */
    List<Money> getDefaultPricePoints(Money price);
}
