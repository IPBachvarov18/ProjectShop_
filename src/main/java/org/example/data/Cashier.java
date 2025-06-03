package org.example.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.regex.Pattern;

public class Cashier implements Serializable {
    private static final String fullNamePattern =
            "^[A-ZА-Я][a-zа-я]{0,12}(?:-(?!-)[A-ZА-Я][a-zа-я]{1,11})?" +
                    "(?: (?:[A-ZА-Я][a-zа-я]{1,14}|[A-ZА-Я][a-zа-я]{0,12}-(?!-)[A-ZА-Я][a-zа-я]{1,11}))?" +
                    " (?:[A-ZА-Я][a-zа-я]{1,14}|[A-ZА-Я][a-zа-я]{0,12}-(?!-)[A-ZА-Я][a-zа-я]{1,11})$";

    private String firstName;
    private String midName;
    private String lastName;
    private BigDecimal salary;
    private final UUID id;
    private CashDesk cashDesk;
    private WorkTime workTime;

    public Cashier(String firstName, String midName, String lastName, BigDecimal salary) {
        this.id = UUID.randomUUID();
        setSalary(salary);
        setFirstName(firstName);
        setMidName(midName);
        setLastName(lastName);
    }

    public Cashier(String fullName, BigDecimal salary) {
        this.id = UUID.randomUUID();
        if (fullName.matches(fullNamePattern)) {
            String[] splittedName = fullName.split(" ");
            if (splittedName.length == 2) {
                setFirstName(splittedName[0]);
                setLastName(splittedName[1]);
            } else if (splittedName.length == 3) {
                setFirstName(splittedName[0]);
                setMidName(splittedName[1]);
                setLastName(splittedName[1]);
            }
        }else{
            throw new IllegalArgumentException("Invalid full name: " + fullName);
        }
        setSalary(salary);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName(){
        return firstName + " " + midName + " " + lastName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        if (cashDesk.getCashier() != null) {
            throw (new IllegalStateException("cashier already assigned"));
        }
        this.cashDesk = cashDesk;

    }

    public WorkTime getWorkTime() {
        return workTime;
    }

    public void setWorkTime(WorkTime workTime) {
        this.workTime = workTime;
    }
}
