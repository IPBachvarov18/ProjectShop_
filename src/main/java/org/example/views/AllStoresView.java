package org.example.views;

import org.example.models.Store;
import org.example.models.WorkTime;
import org.example.utils.table.TableColumn;

import java.time.format.DateTimeFormatter;

public class AllStoresView {

    @TableColumn(header = "Магазин", order = 1, width = 20)
    private String storeName;

    @TableColumn(header = "Раб. време", order = 2, width = 13)
    private String workTimeRange;

    @TableColumn(header = "Брой служители", order = 3, width = 5)
    private String cashierCount;

    @TableColumn(header = "Брой каси", order = 4, width = 5)
    private String cashDeskCount;

    public AllStoresView(Store s) {
        this.storeName = s.getName();

        WorkTime wt = s.getWorkTime();
        if (wt != null && wt.getStartTime() != null && wt.getEndTime() != null) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
            this.workTimeRange = wt.getStartTime().format(fmt)
                    + "-"
                    + wt.getEndTime().format(fmt);
        } else {
            this.workTimeRange = "";
        }

        if (s.getCashiers() != null) {
            this.cashierCount = String.valueOf(s.getCashiers().size());
        } else {
            this.cashierCount = "0";
        }

        if (s.getCashDesks() != null) {
            this.cashDeskCount = String.valueOf(s.getCashDesks().size());
        } else {
            this.cashDeskCount = "0";
        }
    }
}
