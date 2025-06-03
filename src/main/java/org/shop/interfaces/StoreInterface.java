package org.shop.interfaces;

import org.shop.models.Store;

import java.math.BigDecimal;

public interface StoreInterface {
    String getProductQuantitiesInfo(Store store, ProductCatalogService catalog);

    String getStoreCashiers(Store store);

    String getStoreCashDesks(Store store);

    String getCashierWorkingAtCashDesk(Store store);

    String getSalaryExpensesBox(BigDecimal salaryExpenses);

    String getDeliveryExpensesBox(BigDecimal deliveryExpenses);

    String getProductsIncomeBox(BigDecimal deliveryExpenses);

    String getProductsProfitBox(BigDecimal profit);

    String getStoreProfitBox(BigDecimal profit);

    String getReceiptsCount(int count);
}
