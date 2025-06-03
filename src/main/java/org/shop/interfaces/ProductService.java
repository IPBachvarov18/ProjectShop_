package org.shop.interfaces;

import org.shop.models.Product;
import org.shop.models.StoreRequirements;

import java.math.BigDecimal;

public interface ProductService {
    BigDecimal getTotalPrice(StoreRequirements requirements, Product product);
    boolean isExpired(Product product);
}
