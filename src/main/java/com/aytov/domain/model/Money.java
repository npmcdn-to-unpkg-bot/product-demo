package com.aytov.domain.model;

import com.aytov.domain.DomainException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

/**
 * Very basic implementation of Money paradigm.
 */

public final class Money {
    public static final Money ZERO = new Money(BigDecimal.ZERO, "USD");

    private final Currency currency;
    private final BigDecimal value;

    public static Money parse(String moneyStr) {
        String[] tokens = moneyStr.split(" ");
        return new Money(new BigDecimal(tokens[1]), tokens[0]);
    }

    public Money(BigDecimal value, String code) {
        this.value = value.setScale(2, RoundingMode.HALF_UP);

        if (value == null || org.springframework.util.StringUtils.isEmpty(code)) {
            throw new DomainException(DomainException.Type.InvalidProductPrice);
        }

        if (value.signum() < 0) {
            throw new DomainException(DomainException.Type.InvalidProductPrice);
        }

        try {
            this.currency = Currency.getInstance(code);
        } catch (Exception e) {
            throw new DomainException(DomainException.Type.InvalidProductPrice);
        }

        if (!(isGBP()|| isUSD())) {
            throw new DomainException(DomainException.Type.UnsupportedCurrency);
        }
    }

    public boolean isUSD() {
        return "USD".equals(this.getCurrency().getCurrencyCode());
    }

    public boolean isGBP() {
        return "GBP".equals(this.getCurrency().getCurrencyCode());
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getValue() {
        return new BigDecimal(value.doubleValue());
    }

    public BigDecimal getNumberStripped() {
        if (this.value.signum() == 0) {
            return BigDecimal.ZERO;
        }
        return this.value.stripTrailingZeros();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Money) {
            Money other = (Money) obj;
            return Objects.equals(getCurrency(), other.getCurrency()) &&
                    Objects.equals(getNumberStripped(), other.getNumberStripped());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrency(), getNumberStripped());
    }

    @Override
    public String toString() {
        return getCurrency().getCurrencyCode() + ' ' + value.toString();
    }
}
