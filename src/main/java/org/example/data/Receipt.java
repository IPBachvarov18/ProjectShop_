package org.example.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Receipt {
    private int id;
    private Cashier cashier;
    private LocalDateTime time;
    private Set<Product> productsList = new HashSet<>();
    private BigDecimal total;

}
