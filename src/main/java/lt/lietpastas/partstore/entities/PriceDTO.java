package lt.lietpastas.partstore.entities;

import java.math.BigDecimal;

public class PriceDTO {
    private String value;
    private String currency;
    private BigDecimal monetaryValue;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        monetaryValue = new BigDecimal(value.replace(",", "."));
    }

    public BigDecimal getMonetaryValue() {
        return monetaryValue;
    }

    public void setMonetaryValue(BigDecimal monetaryValue) {
        this.monetaryValue = monetaryValue;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
