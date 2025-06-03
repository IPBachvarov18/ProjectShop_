package org.example;

import jdk.jfr.Category;
import org.example.data.*;
import org.example.utils.table.TableRenderer;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
//      --------------------------------------------------------------
        Set<Product> products = new HashSet<>();
        products.add(new Product("Чушки", BigDecimal.valueOf(4), LocalDate.of(2025, 6, 15), ProductCategory.FOOD));
        products.add(new Product("Краставици", BigDecimal.valueOf(3), LocalDate.of(2025, 7, 10), ProductCategory.FOOD));
        products.add(new Product("Моркови", BigDecimal.valueOf(2), LocalDate.of(2025, 8, 5), ProductCategory.FOOD));
        products.add(new Product("Сирене", BigDecimal.valueOf(8), LocalDate.of(2025, 10, 25), ProductCategory.FOOD));
        products.add(new Product("Масло", BigDecimal.valueOf(6), LocalDate.of(2025, 11, 30), ProductCategory.FOOD));
        products.add(new Product("Кафе", BigDecimal.valueOf(10), LocalDate.of(2025, 12, 15), ProductCategory.FOOD));
        products.add(new Product("Боя латекс", BigDecimal.valueOf(35), LocalDate.of(2028, 12, 31), ProductCategory.NON_FOOD));
        products.add(new Product("Тръба PVC", BigDecimal.valueOf(12), LocalDate.of(2030, 1, 1), ProductCategory.NON_FOOD));
        products.add(new Product("Винтове 50 бр.", BigDecimal.valueOf(5), LocalDate.of(2030, 1, 1), ProductCategory.NON_FOOD));
        products.add(new Product("Градински маркуч", BigDecimal.valueOf(20), LocalDate.of(2027, 5, 10), ProductCategory.NON_FOOD));
        products.add(new Product("Фенер акумулаторен", BigDecimal.valueOf(45), LocalDate.of(2029, 12, 31), ProductCategory.NON_FOOD));
        products.add(new Product("Лепило универсално", BigDecimal.valueOf(7), LocalDate.of(2028, 8, 20), ProductCategory.NON_FOOD));
        List<Product> list = new ArrayList<>(products);

        String asciiTable = TableRenderer.renderAsAsciiTable(list);
        System.out.println(asciiTable);

        ProductCatalog productCatalog = new ProductCatalog(products);
        ProductCatalogInterface productCatalogInterface = new ProductCatalogInterface(productCatalog);

        System.out.println("\n\nКаталог със всички налични продукти, налични за доставка към различните магазини:");
        productCatalogInterface.displayAllItems();
//      --------------------------------------------------------------

        Map<CardType, Integer> cardDiscounts = new HashMap<CardType, Integer>();
        cardDiscounts.put(CardType.SILVER, 1);
        cardDiscounts.put(CardType.GOLD, 2);

        ProductService prodService = new ProductServiceImpl();
        Map<ProductCategory, Integer> categoryMarkup = new HashMap<ProductCategory, Integer>();
        categoryMarkup.put(ProductCategory.FOOD, 10);
        categoryMarkup.put(ProductCategory.NON_FOOD, 15);
//      --------------------------------------------------------------

        ReceiptService receiptService = null;
        try {
            receiptService = new ReceiptServiceImpl(prodService);
        } catch (Exception e) {
            System.out.printf("ReceiptService: %s\n", e.getMessage());
        }

        if (receiptService != null) {

            Store store1 = new Store("Lidl", new StoreRequirements(15, 10, categoryMarkup, cardDiscounts));
            Store store2 = new Store("Galenflaund", new StoreRequirements(10, 20, categoryMarkup, cardDiscounts));

            StoreService storeService = new StoreServiceImpl(productCatalog, prodService, receiptService);
            StoreInterface storeInterface = new StoreInterfaceImpl();

            storeService.addProductToStore(store1, list.get(1), 12);
            storeService.addProductToStore(store1, list.get(2), 33);
            storeService.addProductToStore(store1, list.get(7), 2);
            storeService.addProductToStore(store1, list.get(8), 34);

            storeService.addProductToStore(store2, list.get(1), 12);
            storeService.addProductToStore(store2, list.get(10), 8);
            storeService.addProductToStore(store2, list.get(6), 43);
            storeService.addProductToStore(store2, list.get(5), 19);


            System.out.println(storeInterface.getProductQuantitiesInfo(store2, productCatalog));

            CashDesk cashDesk1 = new CashDesk(1);
            CashDesk cashDesk2 = new CashDesk(2);
            Cashier cashier1 = new Cashier("Gosho", "Gogev", "Petrov", BigDecimal.valueOf(500));
            Cashier cashier2 = new Cashier("Preslava Gulgard Petreoba", BigDecimal.valueOf(1500));

            storeService.hireCashier(store1, cashier1);
            storeService.hireCashier(store1, cashier2);

            storeService.makeCashDesk(store1, cashDesk1);
            storeService.makeCashDesk(store1, cashDesk2);

            storeService.assignCashierToCashDesk(store1, cashDesk1.getId(), cashier2.getId());
            storeService.assignCashierToCashDesk(store1, cashDesk2.getId(), cashier1.getId());

            System.out.println(storeInterface.getStoreCashiers(store1));

            System.out.println(storeInterface.getStoreCashDesks(store1));

            System.out.println(storeInterface.getCashierWorkingAtCashDesk(store1));

            Map<Product, Integer> productQuantities = new HashMap<>();
            productQuantities.put(list.get(2), 2);
            productQuantities.put(list.get(4), 3);
            Receipt receipt = null;
            try {
                receipt = storeService.placeOrder(store1, cashDesk1, new ClientData(CardType.GOLD, BigDecimal.valueOf(50), productQuantities));

            } catch (IOException e) {
                System.out.println(e);
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }



            System.out.println(storeInterface.getProductQuantitiesInfo(store1, productCatalog));
            System.out.println(receiptService.getReceiptTxt(store1, receipt));

            System.out.println(cashier1.getSalary());
            System.out.println(cashier2.getSalary());

            System.out.println(storeService.getPayroll(store1));

            System.out.println(storeService.getGoodsDeliveryExpense(store1));
            System.out.println(store1.getTotalIncome());
            System.out.println(storeService.getGoodsDeliveryIncome(store1));

            System.out.println(storeService.getProfit(store1));

            try {
                Receipt receipt22 = receiptService.getReceipt(store1, 1);
                System.out.println(receiptService.getReceiptTxt(store1, receipt22));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }




        }


    }
}