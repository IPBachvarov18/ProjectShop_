package org.shop.interfaces;

import org.shop.models.CashDesk;
import org.shop.models.ClientData;
import org.shop.models.Receipt;
import org.shop.models.Store;

import java.io.IOException;

public interface ReceiptService {
    Receipt generateReceipt(ClientData clientData, Store store, CashDesk cashDesk) throws IOException, ClassNotFoundException;

    Receipt readReceipt();

    int getReceiptCount() throws ClassNotFoundException, IOException;

    String getReceiptTxt(Store store, Receipt receipt);

    Receipt getReceipt(Store store, int receiptId) throws IOException, ClassNotFoundException;
}
