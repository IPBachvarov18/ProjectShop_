package org.example;

import org.example.data.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Set<Product> products = new HashSet<>();

        products.add(new Product("domati", BigDecimal.valueOf(4), LocalDate.of(2025, 6, 15), ProductCategory.FOOD));
        products.add(new Product("krastavici", BigDecimal.valueOf(3), LocalDate.of(2025, 7, 10), ProductCategory.FOOD));
        products.add(new Product("morkovi", BigDecimal.valueOf(2), LocalDate.of(2025, 8, 5), ProductCategory.FOOD));
        products.add(new Product("bulgarche", BigDecimal.valueOf(5), LocalDate.of(2025, 9, 20), ProductCategory.FOOD));
        products.add(new Product("sirene", BigDecimal.valueOf(8), LocalDate.of(2025, 10, 25), ProductCategory.FOOD));
        products.add(new Product("maslo", BigDecimal.valueOf(6), LocalDate.of(2025, 11, 30), ProductCategory.FOOD));
        products.add(new Product("kafe", BigDecimal.valueOf(10), LocalDate.of(2025, 12, 15), ProductCategory.NON_FOOD));
        products.add(new Product("cay", BigDecimal.valueOf(7), LocalDate.of(2026, 1, 10), ProductCategory.NON_FOOD));
        products.add(new Product("zelene", BigDecimal.valueOf(3), LocalDate.of(2026, 2, 5), ProductCategory.FOOD));
        products.add(new Product("hrana_za_ku4eta", BigDecimal.valueOf(12), LocalDate.of(2026, 3, 1), ProductCategory.NON_FOOD));

        ProductCatalog catalog1 = new ProductCatalog(products);

        System.out.println(catalog1);
        Map cardDiscounts = new HashMap<CardType, Integer>();
        cardDiscounts.put(CardType.SILVER, 0.5);
        cardDiscounts.put(CardType.GOLD, 1);

        ProductService prodService = new ProductServiceImpl();
        Map requ = new HashMap<ProductCategory, Integer>();
        requ.put(ProductCategory.FOOD, 1);
        requ.put(ProductCategory.NON_FOOD, 5);
        ReceiptService receiptService = null;

        try {
            receiptService = new ReceiptServiceImpl(prodService);
        } catch (Exception e) {
            System.out.printf("ReceiptService: %s\n", e.getMessage());
        }

        if (receiptService != null) {
            Store store1 = new Store("Lidl", new StoreRequirements(15, 5, requ, cardDiscounts));
            StoreService storeService = new StoreServiceImpl(catalog1, prodService, receiptService);
            StoreInterface storeInterface = new StoreInterfaceImpl();
            List<Product> list = new ArrayList<>(products);
            storeService.addProductToStore(store1, list.get(2), 5);
            storeService.addProductToStore(store1, list.get(4), 5);


            System.out.println(storeInterface.getProductQuantitiesInfo(store1, catalog1));

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



            System.out.println(storeInterface.getProductQuantitiesInfo(store1, catalog1));
            System.out.println(receiptService.getReceiptTxt(store1, receipt));

            System.out.println(cashier1.getSalary());
            System.out.println(cashier2.getSalary());

            System.out.println(storeService.getPayroll(store1));

            System.out.println(storeService.getGoodsDeliveryExpense(store1));
            System.out.println(store1.getTotalIncome());
            System.out.println(storeService.getGoodsDeliveryIncome(store1));

            System.out.println(storeService.getProfit(store1));

            try {
                Receipt receipt22 = receiptService.getReceipt(store1, 12);
                System.out.println(receiptService.getReceiptTxt(store1, receipt22));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }




        }


    }
}