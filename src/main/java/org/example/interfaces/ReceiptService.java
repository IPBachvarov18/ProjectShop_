package org.example.interfaces;

import org.example.models.CashDesk;
import org.example.models.ClientData;
import org.example.models.Receipt;
import org.example.models.Store;

import java.io.IOException;

public interface ReceiptService {
    Receipt generateReceipt(ClientData clientData, Store store, CashDesk cashDesk) throws IOException, ClassNotFoundException;

    Receipt readReceipt();

    int getReceiptCount() throws ClassNotFoundException, IOException;

    String getReceiptTxt(Store store, Receipt receipt);

    Receipt getReceipt(Store store, int receiptId) throws IOException, ClassNotFoundException;
}
