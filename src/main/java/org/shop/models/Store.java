package org.shop.models;

import java.math.BigDecimal;
import java.util.*;

public class Store {

    private UUID id;
    private String name;
    private Set<Cashier> cashiers;
    private Set<CashDesk> cashDesks;
    private StoreRequirements requirements;
    private Map<Product, Quantity> productQuantity;
    private WorkTime workTime;
    private BigDecimal totalIncome;
    private BigDecimal totalProfit;
    private Set<Integer> receiptIds;

    public Map<Product, Quantity> getProductQuantity() {
        return productQuantity;
    }

    public Store(String name, StoreRequirements requirements) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.requirements = requirements;
        this.cashiers = new HashSet<Cashier>();
        this.cashDesks = new HashSet<CashDesk>();
        this.receiptIds = new HashSet<Integer>();
        this.productQuantity = new HashMap<>();
        this.totalIncome = BigDecimal.ZERO;
    }

    public WorkTime getWorkTime() {
        return workTime;
    }

    public void setWorkTime(WorkTime workTime) {
        this.workTime = workTime;
    }

    public void setProductQuantity(Map<Product, Quantity> productQuantity) {
        this.productQuantity = productQuantity;
    }

    public StoreRequirements getRequirements() {
        return requirements;
    }

    public void setRequirements(StoreRequirements requirements) {
        this.requirements = requirements;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Set<CashDesk> getCashDesks() {
        return cashDesks;
    }

    public void setCashDesks(Set<CashDesk> cashDesks) {
        this.cashDesks = cashDesks;
    }

    public Set<Cashier> getCashiers() {
        return cashiers;
    }

    public void setCashiers(Set<Cashier> cashiers) {
        this.cashiers = cashiers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCashDesk(CashDesk cashDesk) {
        this.cashDesks.add(cashDesk);
    }

    public void addCashier(Cashier cashier) {
        this.cashiers.add(cashier);
    }

    public void addProduct(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (!productQuantity.containsKey(product)) {
            productQuantity.put(product, new Quantity(quantity, 0));
        } else {
            productQuantity.compute(product, (k, v) -> {
                v.setAvaibleQunatity(v.getAvaibleQunatity() + quantity);
                return v;
            });
        }
    }

    public void reduceProductQuantity(Product product, int quantity) {
        Quantity q = productQuantity.get(product);
        if (q == null) {
            throw new IllegalArgumentException("Продуктът не е намерен: " + product.getName());
        }

        if (q.getAvaibleQunatity() < quantity) {
            throw new IllegalArgumentException("Няма наличност");
        }

        q.setAvaibleQunatity(q.getAvaibleQunatity() - quantity);
        q.setSoldQunatity(q.getSoldQunatity() + quantity);
    }

    public void addReceipt(int receiptId) {
        this.receiptIds.add(receiptId);
    }

    public Set<Integer> getReceiptIds() {
        return receiptIds;
    }
}
