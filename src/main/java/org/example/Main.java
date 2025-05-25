package org.example;

import org.example.data.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        Map<ProductCategory, Integer> map = new HashMap<ProductCategory, Integer>();
        map.put(ProductCategory.FOOD, 10);
        map.put(ProductCategory.NON_FOOD, 20);

        StoreRequirements req = new StoreRequirements(10, 20,map);
        Product prod1 = new Product("ime", BigDecimal.valueOf(20), date, ProductCategory.FOOD);

        System.out.println(prod1.getDeliveryPrice());
        System.out.println(prod1.getTotalPrice(req));


        Store store = new Store("d",req);
        Cashier cashier = new Cashier("d",BigDecimal.valueOf(1000));
        CashDesk cashDesk=new CashDesk(1);

        System.out.println(store);

        store.hireCashier(cashier);
        store.makeCashDesk(cashDesk);

//        System.out.println(cashier.getCashDesk().getNumber());
//        System.out.println(cashDesk.getCashier().getName());

        store.assignCashierToCashDesk(cashDesk.getId(),cashier.getId());

        System.out.println(cashier.getCashDesk().getNumber());
        System.out.println(cashDesk.getCashier().getName());

        Store.addProductToList(prod1);
        store.addProductToStore(prod1,10);
        store.addProductToStore(prod1,5);
        System.out.println(store.getProductQuantity());


    }
}