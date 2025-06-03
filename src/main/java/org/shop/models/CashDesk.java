package org.shop.models;

import java.io.Serializable;
import java.util.UUID;

public class CashDesk implements Serializable {
    final private UUID id;
    private int number;
    private Cashier cashier;

    public CashDesk(int number) {
        this.id = UUID.randomUUID();
        this.number = number;
    }

    public UUID getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void assignCashierOnCashDesk(Cashier cashier) {
        if (this.cashier!=null) {
            throw (new IllegalStateException("Cash desk already assigned"));
        }
        this.cashier = cashier;
    }

}
