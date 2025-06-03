package org.example.data;

import java.util.List;

public interface StoreInterface {
    String getProductQuantitiesInfo(Store store,ProductCatalogService catalog);

    String getStoreCashiers(Store store);

    String getStoreCashDesks(Store store);

    String getCashierWorkingAtCashDesk(Store store);

    void displayStores(List<Store> stores);

    void displayStore(Store store);

}
