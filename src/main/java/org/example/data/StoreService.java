package org.example.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

public interface StoreService {

    void addProductToStore(Store store, Product product, int quantity);

    void hireCashier(Store store, Cashier cashier);

    void makeCashDesk(Store store, CashDesk cashDesk);

    void assignCashierToCashDesk(Store store, UUID cashDeskId, Cashier cashier);

    void placeOrder(CashDesk cashDesk, ClientData clientData);

    BigDecimal getPayroll(Store store);

    BigDecimal getGoodsDeliveryExpense(Store store);

    BigDecimal getGoodsDeliveryIncome(Store store);

    BigDecimal getProfit(Store store);

    void getReceipt(Store store, int id);


}
