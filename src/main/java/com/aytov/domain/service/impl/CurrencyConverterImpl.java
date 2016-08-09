package com.aytov.domain.service.impl;

import com.aytov.domain.model.Money;
import com.aytov.domain.service.CurrencyConverter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyConverterImpl implements CurrencyConverter {
    @Override
    public List<Money> getDefaultPricePoints(Money price) {
        List<Money> pricePoints = new ArrayList<>(2);
        if (price.isGBP()) {
            pricePoints.add(convertGBPToUSD(price));
        }

        if (price.isUSD()) {
            pricePoints.add(convertUSDToGBP(price));
        }

        return pricePoints;
    }

    private static Money convertUSDToGBP(Money money) {
        return new Money(money.getValue().multiply(new BigDecimal(0.766171975)), "GBP");
    }

    private static Money convertGBPToUSD(Money money) {
        return new Money(money.getValue().multiply(new BigDecimal(1.30519)), "USD");
    }
}
