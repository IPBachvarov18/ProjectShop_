package org.example.data;

import java.io.IOException;

public interface ReceiptService {
    void generateReceipt(Receipt receipt) throws IOException, ClassNotFoundException;

    Receipt readReceipt();

    int getReceiptCount() throws ClassNotFoundException, IOException;

}
