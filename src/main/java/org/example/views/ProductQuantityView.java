package org.example.views;

import org.example.models.Product;
import org.example.models.Quantity;
import org.example.utils.table.TableColumn;

import java.time.format.DateTimeFormatter;

public class ProductQuantityView {
    @TableColumn(header = "Име на продукт", order = 1, width = 25)
    private String productName;

    @TableColumn(header = "Срок на годност", order = 2, width = 12)
    private String expireDate;

    @TableColumn(header = "Категория", order = 3, width = 12)
    private String category;

    @TableColumn(header = "Наличност", order = 4, width = 8)
    private String availableQuantity;

    @TableColumn(header = "Продадени", order = 5, width = 8)
    private String soldQuantity;

    /**
     * Конструктор, който приема Pair<Product, Quantity> (или отделно двата обекта).
     */
    public ProductQuantityView(Product product, Quantity qty) {
        // 1) Име на продукта
        this.productName = product.getName();

        // 2) Форматиране на LocalDate expireDate в "yyyy-MM-dd"
        if (product.getExpireDate() != null) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.expireDate = product.getExpireDate().format(fmt);
        } else {
            this.expireDate = "";
        }

        // 3) Категорията (enum или клас)
        this.category = product.getCategory() != null
                ? product.getCategory().toString()
                : "";

        // 4) Брой в наличност
        this.availableQuantity = String.valueOf(qty.getAvaibleQunatity());

        // 5) Брой продадени
        this.soldQuantity = String.valueOf(qty.getSoldQunatity());
    }
}
