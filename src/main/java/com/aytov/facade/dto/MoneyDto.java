package com.aytov.facade.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyDto implements Transferable {
    private BigDecimal value;
    private String currencyCode;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value.setScale(2, RoundingMode.HALF_UP);
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoneyDto moneyDto = (MoneyDto) o;

        if (value != null ? !value.equals(moneyDto.value) : moneyDto.value != null) return false;
        return currencyCode != null ? currencyCode.equals(moneyDto.currencyCode) : moneyDto.currencyCode == null;

    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (currencyCode != null ? currencyCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MoneyDto{" +
                "value=" + value +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
