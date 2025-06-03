package org.shop.views;

import org.shop.models.Product;
import org.shop.utils.table.TableColumn;

public class OrderProductView {
    @TableColumn(header = "Име на продукт", order = 1, width = 25)
    private String productName;

    @TableColumn(header = "Количество", order = 2, width = 8)
    private String quantity;

    public OrderProductView(Product product, int qty) {
        this.productName = product.getName();
        this.quantity = String.valueOf(qty);
    }
}
