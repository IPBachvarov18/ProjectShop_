package org.example.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Receipt implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Cashier cashier;
    private LocalDateTime time;
    private Set<Product> productsList = new HashSet<>();
    private BigDecimal total;

    public Receipt(int id, Cashier cashier, LocalDateTime time, Set<Product> productsList, BigDecimal total) {
        this.id = id;
        this.cashier = cashier;
        this.time = time;
        this.productsList = productsList;
        this.total = total;
    }

    public Receipt() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Set<Product> getProductsList() {
        return productsList;
    }

    public void setProductsList(Set<Product> productsList) {
        this.productsList = productsList;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Azis";
    }
}
