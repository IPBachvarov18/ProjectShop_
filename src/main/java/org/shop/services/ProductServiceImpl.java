package org.shop.services;

import org.shop.enums.ProductCategory;
import org.shop.interfaces.ProductService;
import org.shop.models.Product;
import org.shop.models.StoreRequirements;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

public class ProductServiceImpl implements ProductService {

    private BigDecimal getMarkupPrice(Map<ProductCategory, Integer> map, Product product) {
        return product.getDeliveryPrice().add(product.getDeliveryPrice().multiply(new BigDecimal(map.get(product.getCategory())).divide(BigDecimal.valueOf(100))));

    }
    @Override
    public BigDecimal getTotalPrice(StoreRequirements requirements, Product product) {
        BigDecimal markupPrice=getMarkupPrice(requirements.getCategoryMarkup(),product);
        LocalDate myObj = LocalDate.now();

        if(myObj.until(product.getExpireDate(), DAYS)<requirements.getDaysUntilExpireDiscount()) {
            return markupPrice.subtract(markupPrice.multiply(new BigDecimal(requirements.getExpireDiscountPercentage())
                    .divide(BigDecimal.valueOf(100))));
        }
        return markupPrice;

    }
    public boolean isExpired(Product product) {
        return product.getExpireDate().isBefore(LocalDate.now());
    }

}
