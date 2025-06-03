package org.shop;

import org.shop.enums.CardType;
import org.shop.enums.ProductCategory;
import org.shop.interfaces.*;
import org.shop.models.*;
import org.shop.services.ProductCatalog;
import org.shop.services.ProductServiceImpl;
import org.shop.services.ReceiptServiceImpl;
import org.shop.views.AllStoresView;
import org.shop.views.OrderProductView;
import org.shop.views.ProductCatalogInterface;
import org.shop.views.StoreInterfaceImpl;
import org.shop.services.StoreServiceImpl;
import org.shop.utils.table.TableRenderer;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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

        ProductCatalog productCatalog = new ProductCatalog(products);
        ProductCatalogInterface productCatalogInterface = new ProductCatalogInterface(productCatalog);

        System.out.println("\n\nКаталог със всички налични продукти, налични за доставка към различните магазини:");
        productCatalogInterface.displayAllItems();
//      --------------------------------------------------------------

        Map<CardType, Integer> cardDiscounts = new HashMap<CardType, Integer>();
        cardDiscounts.put(CardType.SILVER, 4);
        cardDiscounts.put(CardType.GOLD, 8);

        ProductService prodService = new ProductServiceImpl();
        Map<ProductCategory, Integer> categoryMarkup = new HashMap<ProductCategory, Integer>();
        categoryMarkup.put(ProductCategory.FOOD, 12);
        categoryMarkup.put(ProductCategory.NON_FOOD, 18);
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
            store1.setWorkTime(new WorkTime(LocalTime.of(8, 0), LocalTime.of(16, 0)));
            store2.setWorkTime(new WorkTime(LocalTime.of(18, 0), LocalTime.of(3, 0)));

            System.out.println("Списък с всички магазини: ");
            List<AllStoresView> allStoresView = List.of(
                    new AllStoresView(store1),
                    new AllStoresView(store2)
            );
            System.out.println(TableRenderer.renderAsAsciiTable(allStoresView));


            StoreService storeService = new StoreServiceImpl(productCatalog, prodService, receiptService);
            StoreInterface storeInterface = new StoreInterfaceImpl();

            storeService.addProductToStore(store1, list.get(4), 12);
            storeService.addProductToStore(store1, list.get(6), 33);
            storeService.addProductToStore(store1, list.get(2), 11);
            storeService.addProductToStore(store1, list.get(1), 34);

            storeService.addProductToStore(store2, list.get(1), 12);
            storeService.addProductToStore(store2, list.get(10), 8);
            storeService.addProductToStore(store2, list.get(6), 43);
            storeService.addProductToStore(store2, list.get(5), 19);

            System.out.println("\nСписък с продуктите на магазин 1: ");
            System.out.println(storeInterface.getProductQuantitiesInfo(store2, productCatalog));

            CashDesk cashDesk1 = new CashDesk(1);
            CashDesk cashDesk2 = new CashDesk(2);
            Cashier cashier1 = new Cashier("Djena", "Stoeva", "Stoeva", BigDecimal.valueOf(500));
            Cashier cashier2 = new Cashier("Preslava Koleva Ivanova", BigDecimal.valueOf(1500));

            storeService.hireCashier(store1, cashier1);
            storeService.hireCashier(store1, cashier2);
            storeService.makeCashDesk(store1, cashDesk1);
            storeService.makeCashDesk(store1, cashDesk2);

            storeService.assignCashierToCashDesk(store1, cashDesk1.getId(), cashier2.getId());
            storeService.assignCashierToCashDesk(store1, cashDesk2.getId(), cashier1.getId());
            cashier1.setWorkTime(new WorkTime(LocalTime.of(8, 0), LocalTime.of(16, 0)));
            cashier2.setWorkTime(new WorkTime(LocalTime.of(12, 0), LocalTime.of(16, 0)));

            System.out.println("\nКасиери в магазин 1: ");
            System.out.println(storeInterface.getStoreCashiers(store1));

            System.out.println("\nКаси в магазин 1: ");
            System.out.println(storeInterface.getStoreCashDesks(store1));

            Map<Product, Integer> productQuantities = new HashMap<>();
            productQuantities.put(list.get(2), 2);
            productQuantities.put(list.get(1), 3);

            List<OrderProductView> rows = productQuantities.entrySet().stream()
                    .map(e -> new OrderProductView(e.getKey(), e.getValue()))
                    .toList();

            Receipt receipt = null;

            System.out.println("\n\n Продуктите в магазин 1 преди покупка");
            System.out.println(storeInterface.getProductQuantitiesInfo(store1, productCatalog));
            System.out.println("\n Продукти, които предстоят да бъдат закупени");
            System.out.println(TableRenderer.renderAsAsciiTable(rows));

            try {
                receipt = storeService.placeOrder(store1, cashDesk1, new ClientData(CardType.GOLD, BigDecimal.valueOf(1000), productQuantities));
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }

            if (receipt != null) {

                System.out.println("\n\n Продуктите в магазин 1 след покупка");
                System.out.println(storeInterface.getProductQuantitiesInfo(store1, productCatalog));

                System.out.println(receiptService.getReceiptTxt(store1, receipt));

                System.out.println(storeInterface.getReceiptsCount(storeService.getReceiptsCount(store1)));
                System.out.println(storeInterface.getSalaryExpensesBox(storeService.getPayroll(store1)));
                System.out.println(storeInterface.getDeliveryExpensesBox(storeService.getGoodsDeliveryExpense(store1)));
                System.out.println(storeInterface.getProductsIncomeBox(store1.getTotalIncome()));
                System.out.println(storeInterface.getProductsProfitBox(storeService.getGoodsDeliveryIncome(store1)));
                System.out.println(storeInterface.getStoreProfitBox(storeService.getProfit(store1)));

                System.out.println("\n\n\n Зареждане на касова бележка от файл");
//                try {
//                    Receipt receipt22 = receiptService.getReceipt(store1, 1);
//                    System.out.println(receiptService.getReceiptTxt(store1, receipt22));
//                } catch (IOException | ClassNotFoundException e) {
//                    System.out.println(e.getMessage());
//                }
            } else {
                System.out.println("Няма достатъчно пари");
            }
        }
    }
}