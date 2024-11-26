package br.ada.ecommerce.integration.custom;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Invoice {

    @JsonProperty("valor")
    private BigDecimal amount;
    @JsonProperty("vendido_em")
    private LocalDate salesAt;
    @JsonProperty("documento")
    private String document;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getSalesAt() {
        return salesAt;
    }

    public void setSalesAt(LocalDate salesAt) {
        this.salesAt = salesAt;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
