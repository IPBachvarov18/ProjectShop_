package org.example.data;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

public interface StoreService {

    void addProductToStore(Store store, Product product, int quantity);

    void hireCashier(Store store, Cashier cashier);

    void makeCashDesk(Store store, CashDesk cashDesk);

    void assignCashierToCashDesk(Store store, UUID cashDeskId, UUID cashierId);

    Receipt placeOrder(Store store, CashDesk cashDesk, ClientData clientData) throws IOException, ClassNotFoundException;

    BigDecimal getPayroll(Store store);

    BigDecimal getGoodsDeliveryExpense(Store store);

    BigDecimal getGoodsDeliveryIncome(Store store);

    BigDecimal getProfit(Store store);



}
