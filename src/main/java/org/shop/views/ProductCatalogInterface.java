package org.shop.views;

import org.shop.services.ProductCatalog;
import org.shop.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductCatalogInterface {

    ProductCatalog productCatalog;

    public ProductCatalogInterface(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
    }

    private String formatLine(String text, int width) {
        int padding = width - 2 - text.length();
        int padStart = padding / 2;
        int padEnd = padding - padStart;
        return "|" + " ".repeat(padStart) + text + " ".repeat(padEnd) + "|";
    }

    String displayItem(Product product) {
        int boxWidth = 30;
        String lineSep = "-".repeat(boxWidth);

        StringBuilder sb = new StringBuilder();
        sb.append(lineSep).append("\n");
        sb.append(formatLine("Product: " + product.getName(), boxWidth)).append("\n");
        sb.append(formatLine("Price: BGN " + product.getDeliveryPrice(), boxWidth)).append("\n");
        sb.append(formatLine("Expires: " + product.getExpireDate(), boxWidth)).append("\n");
        sb.append(formatLine("Category: " + product.getCategory(), boxWidth)).append("\n");
        sb.append(lineSep);

        return sb.toString();
    }

    public void displayAllItems() {
        int boxesPerRow = 3;
        int boxWidth = 30;
        List<String[]> boxesLines = new ArrayList<>();

        for (Product product : productCatalog.getAllProducts()) {
            String box = displayItem(product);
            String[] lines = box.split("\n");
            boxesLines.add(lines);
        }

        for (int i = 0; i < boxesLines.size(); i += boxesPerRow) {
            int count = Math.min(boxesPerRow, boxesLines.size() - i);
            int linesPerBox = boxesLines.get(i).length;

            for (int lineIndex = 0; lineIndex < linesPerBox; lineIndex++) {
                for (int boxIndex = 0; boxIndex < count; boxIndex++) {
                    System.out.print(boxesLines.get(i + boxIndex)[lineIndex] + "   ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
