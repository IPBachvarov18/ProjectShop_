package org.example.data;

import java.util.UUID;

public interface StoreInterface {
    String getProductQuantitiesInfo(Store store,ProductCatalogService catalog);

    String getStoreCashiers(Store store);

    String getStoreCashDesks(Store store);

    String getCashierWorkingAtCashDesk(Store store);

}
