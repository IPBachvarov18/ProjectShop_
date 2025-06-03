package org.example.views;

import org.example.models.CashDesk;
import org.example.models.Cashier;
import org.example.models.WorkTime;
import org.example.utils.table.TableColumn;

import java.math.BigDecimal;

public class CashierView {
    @TableColumn(header = "Име", order = 1, width = 20)
    private String name;

    @TableColumn(header = "Номер на каса", order = 2, width = 5)
    private String deskNumber;

    @TableColumn(header = "Раб. време", order = 3, width = 11)
    private String workTime;

    @TableColumn(header = "Заплата", order = 4, width = 10)
    private String salary;

    public CashierView(Cashier c) {
        this.name = c.getFullName();

        CashDesk desk = c.getCashDesk();
        if (desk != null) {
            this.deskNumber = String.valueOf(desk.getNumber());
        } else {
            this.deskNumber = "-";
        }

        WorkTime wt = c.getWorkTime();
        if (wt != null) {
            this.workTime = wt.toString();
        } else {
            this.workTime = "";
        }

        BigDecimal sal = c.getSalary();
        this.salary = (sal != null) ? sal.toString() : "";
        this.salary = "BGN " + this.salary;
    }
}
