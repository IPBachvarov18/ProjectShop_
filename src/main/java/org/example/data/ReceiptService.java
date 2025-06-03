package org.example.data;

import java.io.IOException;

public interface ReceiptService {
    Receipt generateReceipt(ClientData clientData, Store store,CashDesk cashDesk) throws IOException, ClassNotFoundException;

    Receipt readReceipt();

    int getReceiptCount() throws ClassNotFoundException, IOException;


    String getReceiptTxt(Store store, Receipt receipt);
}
