package org.example.data;

import java.math.BigDecimal;
import java.util.*;

public class Store {

    private UUID id;

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

    private String name;
    private Set<Cashier> cashiers;
    private Set<CashDesk> cashDesks;
    private StoreRequirements requirements;
    private Map<UUID, Quantity> productQuantity;
    private WorkTime workTime;
    private BigDecimal totalIncome;
    private BigDecimal totalProfit;

    public Map<UUID, Quantity> getProductQuantity() {
        return productQuantity;
    }

    public Store(String name, StoreRequirements requirements) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.requirements = requirements;
        this.cashiers = new HashSet<Cashier>();
        this.cashDesks = new HashSet<CashDesk>();
        this.productQuantity = new HashMap<>();
    }

    public WorkTime getWorkTime() {
        return workTime;
    }

    public void setWorkTime(WorkTime workTime) {
        this.workTime = workTime;
    }

    public void setProductQuantity(Map<UUID, Quantity> productQuantity) {
        this.productQuantity = productQuantity;
    }

    public StoreRequirements getRequirements() {
        return requirements;
    }

    public void setRequirements(StoreRequirements requirements) {
        this.requirements = requirements;
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
        if (!productQuantity.containsKey(product.getId())) {
            productQuantity.put(product.getId(), new Quantity(quantity, 0));
        } else {
            productQuantity.compute(product.getId(), (k, v) -> {
                v.setAvaibleQunatity(v.getAvaibleQunatity() + quantity);
                 return v;
            });
        }
    }

    void reduceProductQuantity(UUID productId, int quantity) {
        if (productQuantity.get(productId).getAvaibleQunatity() <= quantity) {
            productQuantity.compute(productId, (k, v) -> {
                v.setAvaibleQunatity(v.getAvaibleQunatity() - quantity);
                v.setSoldQunatity(v.getSoldQunatity() + quantity);
                return v;
            });
        } else {
            throw new IllegalArgumentException("Not enough product quantity");
        }
    }

}
