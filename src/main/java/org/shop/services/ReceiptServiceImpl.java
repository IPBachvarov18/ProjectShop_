package org.shop.services;

import org.shop.interfaces.ProductService;
import org.shop.interfaces.ReceiptService;
import org.shop.models.*;
import org.shop.repository.FileRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReceiptServiceImpl implements ReceiptService {
    FileRepository<Receipt> receiptRepository;
    FileRepository<Integer> receiptCountRepository;
    ProductService productService;
    private final String receiptCountFileName = "receiptCount.bin";
    private final String binAddress = "receiptCount.bin";
    private final String txtAddress = "receiptCount.bin";
    private final String sysAddress = "receiptCount.bin";


    public ReceiptServiceImpl(ProductService productService) throws IOException {
        receiptRepository = new FileRepository<Receipt>(Path.of(txtAddress), Path.of(binAddress));
        receiptCountRepository = new FileRepository<Integer>(Path.of(sysAddress), Path.of(sysAddress));
        if (!receiptCountRepository.doesBinFileExist(receiptCountFileName)) {
            receiptCountRepository.writeBin(0, receiptCountFileName);
        }
        this.productService = productService;
    }

    @Override
    public Receipt generateReceipt(ClientData clientData, Store store, CashDesk cashDesk) throws IOException, ClassNotFoundException {
        int order = this.getReceiptCount();
        Receipt receipt = new Receipt(++order, cashDesk.getCashier(), LocalDateTime.now(), clientData.getProductList(), clientData.getTotal(),clientData.getTotalDiscount(), store.getName());
        String receiptFileName = "Receipt_" + order;
        receiptCountRepository.writeBin(order, receiptCountFileName);
        receiptRepository.writeBin(receipt, receiptFileName + ".bin");

        receiptRepository.writeTxt(this.getReceiptTxt(store, receipt), receiptFileName + ".txt");

        return receipt;
    }

    @Override
    public Receipt readReceipt() {
        return null;
    }

    @Override
    public int getReceiptCount() throws ClassNotFoundException, IOException {
        return (int) receiptCountRepository.readBin(receiptCountFileName);
    }

    public String getReceiptTxt(Store store, Receipt receipt) {
        List<String> lines = new ArrayList<>();
        lines.add("                        Касов Бон ");
        lines.add("Магазин: " + receipt.getStoreName());
        lines.add("Пореден Номер #" + receipt.getId());
        lines.add("Касиер: " + (receipt.getCashier() != null
                ? receipt.getCashier().getFullName()
                : "Unknown"));

        lines.add("Дата: " + (receipt.getTime() != null
                ? receipt.getTime().toString()
                : "Unknown"));
        lines.add("-----------------------------------------------------------");
        lines.add(String.format("%-20s %10s %15s %15s",
                "Продукт", "Количество", "Ед. Цена", "Общо"));
        lines.add("-----------------------------------------------------------");

        for (Map.Entry<Product, Integer> entry : receipt.getProductsList().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            if (product != null) {
                BigDecimal unitPrice = productService.getTotalPrice(store.getRequirements(), product);
                BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));

                lines.add(String.format("%-20s %10d %15.2f %15.2f",
                        product.getName(), quantity, unitPrice, totalPrice));
            } else {
                lines.add(String.format("%-20s %10d %15s %15s",
                        "Unknown Product", quantity, "N/A", "N/A"));
            }
        }

        lines.add("-----------------------------------------------------------");
        lines.add(String.format("%-45s %15.2f",
                "Отстъпка:", receipt.getTotalDiscount() != null
                        ? receipt.getTotalDiscount()
                        : BigDecimal.ZERO));
        lines.add(String.format("%-45s %15.2f",
                "Общо:", receipt.getTotal() != null
                        ? receipt.getTotal()
                        : BigDecimal.ZERO));

        int maxLen = 0;
        for (String line : lines) {
            if (line.length() > maxLen) {
                maxLen = line.length();
            }
        }

        String border = "+" + "-".repeat(maxLen + 2) + "+";

        StringBuilder sb = new StringBuilder();
        sb.append(border).append("\n");
        for (String line : lines) {
            sb.append("| ").append(line);
            int padding = maxLen - line.length();
            if (padding > 0) {
                sb.append(" ".repeat(padding));
            }
            sb.append(" |\n");
        }
        sb.append(border);

        return sb.toString();
    }


    @Override
    public Receipt getReceipt(Store store, int receiptId) throws IOException, ClassNotFoundException {
        return (Receipt) receiptRepository.readBin("Receipt_" + receiptId + ".bin");
    }

}
