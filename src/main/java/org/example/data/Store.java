package org.example.data;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

public class Store {

    private UUID id;
    private String name;
    private Set<Cashier> cashiers;
    private Set<CashDesk> cashDesks;
    private StoreRequirements requirements;
    private Map<UUID, Integer> productQuantity;
    private static Set<Product> productsList = new HashSet<>();
    private WorkTime workTime;

    public Map<UUID, Integer> getProductQuantity() {
        return productQuantity;
    }

    public Store(String name, StoreRequirements requirements) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.requirements = requirements;
        this.cashiers = new HashSet<Cashier>();
        this.cashDesks = new HashSet<CashDesk>();
        this.productQuantity = new HashMap<>();
    }

    static public void addProductToList(Product product) {
        if (!productsList.add(product)) {
            System.out.println("Throw something");
        }

    }

    public void addProductToStore(Product product, int quantity) {
        if (!productsList.contains(product)) {
            System.out.println("Throw something");
        }
        if (!productQuantity.containsKey(product.getId())) {
            productQuantity.put(product.getId(), quantity);
        } else {
            productQuantity.compute(product.getId(), (k, v) -> productQuantity.get(k) + quantity);
        }

    }


    public void hireCashier(Cashier cashier) {
        if (!this.cashiers.add(cashier)) {
            System.out.println("Throw exception");
        }
    }

    public void makeCashDesk(CashDesk cashDesk) {
        if (!this.cashDesks.add(cashDesk)) {
            System.out.println("Throw exception");
        }

    }


    public void assignCashierToCashDesk(UUID cashDeskId, UUID cashierId) {


        Cashier cashier = cashiers.stream().filter(c -> c.getId() == cashierId)
                .findFirst()
                .orElseThrow();

        CashDesk cashDesk = cashDesks.stream().filter(c -> c.getId() == cashDeskId)
                .findFirst()
                .orElseThrow();

        if (cashier.getCashDesk() != null || cashDesk.getCashier() != null) {
            System.out.println("Throw exception");
            return;
        }

        cashier.assignCashDeskOnCashier(cashDesk);
        cashDesk.assignCashierOnCashDesk(cashier);
    }

//    public void placeOrder(CashDesk cashDesk, HashMap<UUID, Integer> productList, BigDecimal avaiableCash) {
//        if (!this.cashDesks.contains(cashDesk) && cashDesk.getCashier() == null) {
//            System.out.println("Throw exception");
//            return;
//        }
//
//        boolean enoughQunatity = productList.entrySet().stream().allMatch((e) ->
//                e.getValue() <= this.productQuantity.get(e.getKey())
//        );
//        if (!enoughQunatity) {
//            System.out.println("Throw exception");
//            return;
//        }
//        productList.entrySet().stream().
//                map((e) -> {
//                    UUID id = e.getKey();
//                    Product pr = productsList.stream().filter((p) -> p.getId() == id)
//                            .findFirst()
//                            .orElseThrow();
//                    BigDecimal sum = pr.getTotalPrice(requirements);
//                    int qunatity = e.getValue();
//
//                    BigDecimal total = sum.multiply(new BigDecimal(qunatity));
//                    return total;
//
//                }).reduce(BigDecimal.ZERO, BigDecimal::add);
//
//
//    }

}
