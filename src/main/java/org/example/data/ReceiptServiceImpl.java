package org.example.data;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;

public class ReceiptServiceImpl implements ReceiptService {
    FileRepository receiptRepository;
    FileRepository receiptCountRepository;
    ProductService productService;
    private String receiptCountFileName = "receiptCount.bin";


    public ReceiptServiceImpl(ProductService productService) throws IOException {
        receiptRepository = new FileRepository<Receipt>(Path.of("F:\\kone\\txt"), Path.of("F:\\kone\\bin"));
        receiptCountRepository = new FileRepository<Integer>(Path.of("F:\\kone\\sys"), Path.of("F:\\kone\\sys"));
        if (!receiptCountRepository.doesBinFileExist(receiptCountFileName)) {
            receiptCountRepository.writeBin(0, receiptCountFileName);
        }
        this.productService = productService;
    }

    @Override
    public Receipt generateReceipt(ClientData clientData, Store store, CashDesk cashDesk) throws IOException, ClassNotFoundException {
        int order = this.getReceiptCount();
        Receipt receipt = new Receipt(++order, cashDesk.getCashier(), LocalDateTime.now(), clientData.getProductList(), clientData.getTotal(),clientData.getTotalDiscount());
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
        StringBuilder sb = new StringBuilder();

        sb.append("Receipt #").append(receipt.getId()).append("\n");
        sb.append("Cashier: ").append(receipt.getCashier() != null ? receipt.getCashier().getFullName() : "Unknown").append("\n");
        sb.append("Date: ").append(receipt.getTime() != null ? receipt.getTime().toString() : "Unknown").append("\n");
        sb.append("-----------------------------------------------------------\n");
        sb.append(String.format("%-20s %10s %15s %15s\n", "Product", "Quantity", "Unit Price", "Total Price"));
        sb.append("-----------------------------------------------------------\n");

        for (Map.Entry<Product, Integer> entry : receipt.getProductsList().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            if (product != null) {
                BigDecimal unitPrice = productService.getTotalPrice(store.getRequirements(), product);
                BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));

                sb.append(String.format("%-20s %10d %15.2f %15.2f\n",
                        product.getName(), quantity, unitPrice, totalPrice));
            } else {
                sb.append(String.format("%-20s %10d %15s %15s\n",
                        "Unknown Product", quantity, "N/A", "N/A"));
            }
        }

        sb.append("-----------------------------------------------------------\n");
        sb.append(String.format("%-45s %15.2f\n",
                "Total discount:", receipt.getTotalDiscount() != null ? receipt.getTotalDiscount() : BigDecimal.ZERO));
        sb.append(String.format("%-45s %15.2f\n",
                "Total:", receipt.getTotal() != null ? receipt.getTotal() : BigDecimal.ZERO));

        return sb.toString();
    }

    @Override
    public Receipt getReceipt(Store store, int receiptId) throws IOException, ClassNotFoundException {
        return (Receipt) receiptRepository.readBin("Receipt_" + receiptId + ".bin");
    }

}
