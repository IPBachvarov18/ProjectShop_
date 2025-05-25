package org.example.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;


public class Product implements Salable {
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

    private BigDecimal getMarkupPrice(Map<ProductCategory, Integer> map) {
        return deliveryPrice.add(deliveryPrice.multiply(new BigDecimal(map.get(category)).divide(BigDecimal.valueOf(100))));

    }
    @Override
    public BigDecimal getTotalPrice(StoreRequirements requirements) {
        BigDecimal markupPrice=getMarkupPrice(requirements.getCategoryMarkup());
        LocalDate myObj = LocalDate.now();

        if(myObj.until(expireDate, DAYS)<requirements.getDaysUntilExpireDiscount()) {
            return markupPrice.subtract(markupPrice.multiply(new BigDecimal(requirements.getExpireDiscountPercentage())
                    .divide(BigDecimal.valueOf(100))));
        }
        return markupPrice;

    }
    public boolean isExpired() {
        if(expireDate.isBefore(LocalDate.now())) {
            return true;
        }
        return false;
    }

}
