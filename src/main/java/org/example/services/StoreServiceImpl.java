package org.example.services;

import org.example.interfaces.ProductService;
import org.example.interfaces.ReceiptService;
import org.example.interfaces.StoreService;
import org.example.models.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

public class StoreServiceImpl implements StoreService {
    ReceiptService receiptService;
    ProductCatalog productCatalog;
    ProductService productService;

    public StoreServiceImpl(ProductCatalog productCatalog, ProductService productService, ReceiptService receiptService) {
        this.productCatalog = productCatalog;
        this.productService = productService;
        this.receiptService = receiptService;

    }

    public void addProductToStore(Store store, Product product, int quantity) {
        if (productCatalog.getProductById(product.getId()) == null) {
            throw (new RuntimeException("Product with id " + product.getId() + " does not exist"));
        } else {
            store.addProduct(product, quantity);
        }
    }

    @Override
    public void hireCashier(Store store, Cashier cashier) {
        if (!store.getCashiers().contains(cashier)) {
            store.addCashier(cashier);
        } else {
            throw (new RuntimeException("Cashier already hired"));
        }
    }

    @Override
    public void makeCashDesk(Store store, CashDesk cashDesk) {
        if (!store.getCashDesks().contains(cashDesk)) {
            store.addCashDesk(cashDesk);
        } else {
            throw (new RuntimeException("CashDesk already made"));
        }
    }

    @Override
    public void assignCashierToCashDesk(Store store, UUID cashDeskId, UUID cashierId) {
        Cashier cashier = store.getCashiers().stream().filter(c -> c.getId() == cashierId)
                .findFirst()
                .orElseThrow();

        CashDesk cashDesk = store.getCashDesks().stream().filter(c -> c.getId() == cashDeskId)
                .findFirst()
                .orElseThrow();

        if (cashier.getCashDesk() != null || cashDesk.getCashier() != null) {
            System.out.println("Throw exception");
            return;
        }

        cashier.assignCashDeskOnCashier(cashDesk);
        cashDesk.assignCashierOnCashDesk(cashier);

    }

    @Override
    public Receipt placeOrder(Store store, CashDesk cashDesk, ClientData clientData) throws IOException, ClassNotFoundException {

        BigDecimal sum = clientData.getProductList().entrySet().stream().
                map((e) -> {
                    Product pr = e.getKey();
                    BigDecimal sum1 = productService.getTotalPrice(store.getRequirements(), pr);
                    int qunatity = e.getValue();

                    BigDecimal total = sum1.multiply(new BigDecimal(qunatity));
                    return total;

                }).reduce(BigDecimal.ZERO, BigDecimal::add);

        clientData.setTotal(sum.subtract(sum.multiply(BigDecimal.valueOf(store.getRequirements().getCardTypeDiscount()
                .get(clientData.getCard())).divide(BigDecimal.valueOf(100)))));
        clientData.setTotalDiscount(sum.multiply(BigDecimal.valueOf(store.getRequirements().getCardTypeDiscount()
                .get(clientData.getCard())).divide(BigDecimal.valueOf(100))));

        if (clientData.getTotal().compareTo(clientData.getAvaiableCash()) != 1) {
            clientData.getProductList().entrySet().stream()
                    .forEach(entry -> {
                        Product key = entry.getKey();
                        Integer value = entry.getValue();
                        store.reduceProductQuantity(key, value);
                    });

            store.setTotalIncome(clientData.getTotal());
            Receipt receipt = receiptService.generateReceipt(clientData, store, cashDesk);
            store.addReceipt(receipt.getId());
            return receipt;
        }
        return null;
    }

    void hireCashier(Cashier cashier, Store store) {
        if (store.getCashiers().contains(cashier)) {
            throw (new RuntimeException("Cashier already exists"));
        } else {
            store.addCashier(cashier);
        }
    }

    void makeCashDesk(CashDesk cashDesk, Store store) {
        if (store.getCashDesks().contains(cashDesk)) {
            throw (new RuntimeException("CashDesk already exists"));
        } else {
            store.addCashDesk(cashDesk);
        }
    }

    public void assignCashierToCashDesk(CashDesk cashDesk, Store store, Cashier cashier) {

        LocalTime timeNow = LocalTime.now();
        if (timeNow.isBefore(cashier.getWorkTime().getEndTime()) && timeNow.isAfter(cashier.getWorkTime().getStartTime())) {
            cashDesk.assignCashierOnCashDesk(cashier);
            cashier.assignCashDeskOnCashier(cashDesk);
        } else {
            throw (new RuntimeException("Cashier out of work time"));
        }


    }

    // pravim belejka
        public BigDecimal getPayroll(Store store) {
            BigDecimal payroll = BigDecimal.ZERO;
            for (Cashier cashier : store.getCashiers()) {
                payroll = payroll.add(cashier.getSalary());
            }
            return payroll;
        }

    public BigDecimal getGoodsDeliveryExpense(Store store) {
        return store.getProductQuantity().entrySet().stream().
                map((e) -> {
                    Product pr = e.getKey();
                    BigDecimal deliveryPrice = pr.getDeliveryPrice();
                    int qunatity = e.getValue().getAvaibleQunatity() + e.getValue().getSoldQunatity();

                    BigDecimal total = deliveryPrice.multiply(new BigDecimal(qunatity));
                    return total;

                }).reduce(BigDecimal.ZERO, BigDecimal::add);


    }

    @Override
    public BigDecimal getGoodsDeliveryIncome(Store store) {
        BigDecimal expenses= store.getProductQuantity().entrySet().stream().
                map((e) -> {
                    int totalQunatity = e.getValue().getSoldQunatity()+e.getValue().getAvaibleQunatity();
                    BigDecimal deliveryPrice = e.getKey().getDeliveryPrice();

                    BigDecimal total = deliveryPrice.multiply(new BigDecimal(totalQunatity));
                    return total;

                }).reduce(BigDecimal.ZERO, BigDecimal::add);
        return store.getTotalIncome().subtract(expenses);
    }

    @Override
    public BigDecimal getProfit(Store store) {
        BigDecimal totalProfit=getGoodsDeliveryIncome(store);

        return totalProfit.subtract(this.getPayroll(store));
    }

    public int getReceiptsCount(Store store) {
        return store.getReceiptIds().size();
    }


}



