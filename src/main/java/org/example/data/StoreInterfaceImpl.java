package org.example.data;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class StoreInterfaceImpl implements StoreInterface {

    public String getProductQuantitiesInfo(Store store, ProductCatalogService catalog) {

        StringBuilder sb = new StringBuilder();
        sb.append("Product Inventory:\n");
        sb.append("------------------\n");

        for (Map.Entry<UUID, Quantity> entry : store.getProductQuantity().entrySet()) {
            UUID productId = entry.getKey();
            Quantity qty = entry.getValue();

            Product product = catalog.getProductById(productId);
            if (product != null) {
                sb.append(String.format("Name: %-15s | Available: %3d | Sold: %3d%n",
                        product.getName(), qty.getAvaibleQunatity(), qty.getSoldQunatity()));
            } else {
                sb.append("Unknown product with ID: ").append(productId).append("\n");
            }
        }

        return sb.toString();
    }

    @Override
    public String getStoreCashiers(Store store) {
        StringBuilder sb = new StringBuilder();
        sb.append("Cashiers in store ").append(store.getName()).append(":\n");
        sb.append("--------------------------------------\n");

        Set<Cashier> cashiers = store.getCashiers();
        if (cashiers == null || cashiers.isEmpty()) {
            sb.append("No cashiers assigned.\n");
            return sb.toString();
        }

        for (Cashier cashier : cashiers) {
            sb.append("Name: ").append(cashier.getName());

            CashDesk desk = cashier.getCashDesk();
            if (desk != null) {
                sb.append(", Cash Desk: ").append(desk.getNumber());
            } else {
                sb.append(", Not assigned to a cash desk");
            }

            WorkTime wt = cashier.getWorkTime();
            if (wt != null) {
                sb.append(", Work Time: ").append(wt.toString());
            }

            sb.append("\n");
        }

        return sb.toString();
    }


    @Override
    public String getStoreCashDesks(Store store) {
        StringBuilder sb = new StringBuilder();
        sb.append("Cash Desks in store ").append(store.getName()).append(":\n");
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
                sb.append(", Cashier: ").append(cashier.getName());
            } else {
                sb.append(", No cashier assigned");
            }
            sb.append("\n");
        }

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
                sb.append(", Cashier: ").append(cashier.getName());
            } else {
                sb.append(", No cashier assigned");
            }
            sb.append("\n");
        }

        return sb.toString();
    }



}
