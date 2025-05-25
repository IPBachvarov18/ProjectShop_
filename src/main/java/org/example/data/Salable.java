package org.example.data;

import java.math.BigDecimal;

public interface Salable {
    BigDecimal getTotalPrice(StoreRequirements requirements);
    boolean isExpired();
}
