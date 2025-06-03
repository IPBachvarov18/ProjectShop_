package org.shop.views;

import org.shop.models.CashDesk;
import org.shop.models.Cashier;
import org.shop.utils.table.TableColumn;

public class CashDeskView {
    @TableColumn(header = "Номер на каса", order = 1, width = 5)
    private String deskNumber;

    @TableColumn(header = "Име на касиер", order = 2, width = 20)
    private String cashierName;

    public CashDeskView(CashDesk desk) {
        this.deskNumber = String.valueOf(desk.getNumber());

        Cashier c = desk.getCashier();
        this.cashierName = (c != null) ? c.getFullName() : "-";
    }
}
