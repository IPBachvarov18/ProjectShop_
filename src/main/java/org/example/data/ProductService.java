package org.example.data;

import java.math.BigDecimal;

public interface ProductService {
    BigDecimal getTotalPrice(StoreRequirements requirements,Product product);
    boolean isExpired(Product product);
}
