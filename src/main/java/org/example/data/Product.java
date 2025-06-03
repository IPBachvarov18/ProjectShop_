package org.example.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;


public class Product implements Serializable {
    private final UUID id;
    private String name;
    private BigDecimal deliveryPrice;
    private LocalDate expireDate;
    private ProductCategory category;

    public Product(String name, BigDecimal deliveryPrice, LocalDate expireDate,ProductCategory category) {
        this.setName(name);
        this.setDeliveryPrice(deliveryPrice);
        this.setExpireDate(expireDate);
        this.id = UUID.randomUUID();
        this.category = category;

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public ProductCategory getCategory() {
        return category;
    }
}
