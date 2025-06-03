package org.example.views;

import org.example.interfaces.ProductCatalogService;
import org.example.interfaces.StoreInterface;
import org.example.models.*;
import org.example.utils.table.TableRenderer;

import java.util.List;
import java.util.Map;
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
    public void displayStores(List<Store> stores) {

    }

    @Override
    public void displayStore(Store store) {

    }


}
