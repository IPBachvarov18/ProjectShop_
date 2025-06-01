package org.example.data;

public class Quantity {
    private int avaibleQunatity;
    private int soldQunatity;

    public Quantity(int avaibleQunatity, int soldQunatity) {
        this.avaibleQunatity = avaibleQunatity;
        this.soldQunatity = soldQunatity;
    }

    public int getAvaibleQunatity() {
        return avaibleQunatity;
    }

    public void setAvaibleQunatity(int avaibleQunatity) {
        this.avaibleQunatity = avaibleQunatity;
    }

    public int getSoldQunatity() {
        return soldQunatity;
    }

    public void setSoldQunatity(int soldQunatity) {
        this.soldQunatity = soldQunatity;
    }
}
