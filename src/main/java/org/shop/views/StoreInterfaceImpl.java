package org.shop.views;

import org.shop.interfaces.ProductCatalogService;
import org.shop.interfaces.StoreInterface;
import org.shop.models.*;
import org.shop.utils.table.TableRenderer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StoreInterfaceImpl implements StoreInterface {


    public String getProductQuantitiesInfo(Store store, ProductCatalogService catalog) {

        List<ProductQuantityView> rows = store.getProductQuantity()
                .entrySet().stream()
                .map(e -> new ProductQuantityView(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        return TableRenderer.renderAsAsciiTable(rows);
    }


    @Override
    public String getStoreCashiers(Store store) {
        StringBuilder sb = new StringBuilder();
        Set<Cashier> cashiers = store.getCashiers();
        if (cashiers == null || cashiers.isEmpty()) {
            sb.append("No cashiers assigned.\n");
            return sb.toString();
        }

        List<CashierView> rows = cashiers.stream()
                .map(CashierView::new)
                .collect(Collectors.toList());

        sb.append(TableRenderer.renderAsAsciiTable(rows));
        return sb.toString();
    }


    @Override
    public String getStoreCashDesks(Store store) {
        StringBuilder sb = new StringBuilder();
        Set<CashDesk> cashDesks = store.getCashDesks();
        if (cashDesks == null || cashDesks.isEmpty()) {
            sb.append("No cash desks available.\n");
            return sb.toString();
        }

        List<CashDeskView> rows = cashDesks.stream()
                .map(CashDeskView::new)
                .collect(Collectors.toList());

        sb.append(TableRenderer.renderAsAsciiTable(rows));
        return sb.toString();
    }


    @Override
    public String getCashierWorkingAtCashDesk(Store store) {
        StringBuilder sb = new StringBuilder();
        sb.append("Cashiers working at cash desks in store ").append(store.getName()).append(":\n");
        sb.append("--------------------------------------\n");

        Set<CashDesk> cashDesks = store.getCashDesks();
        if (cashDesks == null || cashDesks.isEmpty()) {
            sb.append("No cash desks available.\n");
            return sb.toString();
        }

        for (CashDesk desk : cashDesks) {
            sb.append("Cash Desk Number: ").append(desk.getNumber());
            Cashier cashier = desk.getCashier();
            if (cashier != null) {
                sb.append(", Cashier: ").append(cashier.getFullName());
            } else {
                sb.append(", No cashier assigned");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public String getSalaryExpensesBox(BigDecimal salaryExpenses) {
        String content = "Разходи за заплати: BGN "
                + salaryExpenses.setScale(2, RoundingMode.HALF_UP);
        return displayBox(content);
    }

    @Override
    public String getDeliveryExpensesBox(BigDecimal deliveryExpenses) {
        String content = "Разходи за доставка на продукти: BGN "
                + deliveryExpenses.setScale(2, RoundingMode.HALF_UP);
        return displayBox(content);
    }

    @Override
    public String getProductsIncomeBox(BigDecimal totalRevenue) {
        String content = "Приходи от продадени стоки: BGN "
                + totalRevenue.setScale(2, RoundingMode.HALF_UP);
        return displayBox(content);
    }

    @Override
    public String getProductsProfitBox(BigDecimal profit) {
        String content = "Печалба от продадени стоки: BGN "
                + profit.setScale(2, RoundingMode.HALF_UP);
        return displayBox(content);
    }

    @Override
    public String getStoreProfitBox(BigDecimal profit) {
        String content = "Печалба на магазина: BGN "
                + profit.setScale(2, RoundingMode.HALF_UP);
        return displayBox(content);
    }

    @Override
    public String getReceiptsCount(int count) {
        String content = "Брой издадени касови бележки: " + count;
        return displayBox(content);
    }

    private String displayBox(String content) {
        int maxLen = content.length();
        String border = "+" + "-".repeat(maxLen + 2) + "+";

        StringBuilder sb = new StringBuilder();
        sb.append(border).append("\n");
        sb.append("| ").append(content).append(" |\n");
        sb.append(border);

        return sb.toString();
    }

}
