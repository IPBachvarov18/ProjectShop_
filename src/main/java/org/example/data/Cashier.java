package org.example.data;

import java.math.BigDecimal;
import java.util.UUID;

public class Cashier {
    private String name;
    private BigDecimal salary;
    private final UUID id;
    private CashDesk cashDesk;

    public Cashier(String name, BigDecimal salary) {
        this.id = UUID.randomUUID();
        setSalary(salary);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public UUID getId() {
        return id;
    }
    public CashDesk getCashDesk() {
        return cashDesk;
    }

    void assignCashDeskOnCashier(CashDesk cashDesk) {
        this.cashDesk=cashDesk;

    }



}
