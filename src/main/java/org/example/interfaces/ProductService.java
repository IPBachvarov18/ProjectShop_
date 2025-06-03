package org.example.interfaces;

import org.example.models.Product;
import org.example.models.StoreRequirements;

import java.math.BigDecimal;

public interface ProductService {
    BigDecimal getTotalPrice(StoreRequirements requirements, Product product);
    boolean isExpired(Product product);
}
