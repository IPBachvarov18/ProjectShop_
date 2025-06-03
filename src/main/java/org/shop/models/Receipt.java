package org.shop.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class Receipt implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Cashier cashier;
    private String storeName;
    private LocalDateTime time;
    private Map<Product,Integer> productsList = new HashMap<>();
    private BigDecimal total;
    private BigDecimal totalDiscount;

    public Receipt(int id, Cashier cashier, LocalDateTime time, Map<Product,Integer> productsList, BigDecimal total, BigDecimal totalDiscount, String storeName) {
        this.id = id;
        this.cashier = cashier;
        this.time = time;
        this.productsList = productsList;
        this.total = total;
        this.totalDiscount = totalDiscount;
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public Map<Product,Integer> getProductsList() {
        return productsList;
    }

    public void setProductsList(Map<Product,Integer> productsList) {
        this.productsList = productsList;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    @Override
    public String toString() {
        return "a";
    }
}
