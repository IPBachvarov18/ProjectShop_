package org.example.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReceiptServiceImpl implements ReceiptService {
    FileRepository receiptRepository;
    FileRepository receiptCountRepository;
    private String receiptCountFileName = "receiptCount.bin";

    public ReceiptServiceImpl() throws IOException {
        receiptRepository = new FileRepository<Receipt>(Path.of("F:\\kone\\txt"), Path.of("F:\\kone\\bin"));
        receiptCountRepository = new FileRepository<Integer>(Path.of("F:\\kone\\sys"), Path.of("F:\\kone\\sys"));
        if(!receiptCountRepository.doesBinFileExist(receiptCountFileName)){
            receiptCountRepository.writeBin(0, receiptCountFileName);
        }
    }

    @Override
    public void generateReceipt(Receipt receipt) throws IOException, ClassNotFoundException {
        int order = this.getReceiptCount();
        String receiptFileName = "Receipt_" + ++order;
        receiptCountRepository.writeBin(order, receiptCountFileName);
        receiptRepository.writeBin(receipt, receiptFileName+ ".bin");
        receiptRepository.writeTxt(receipt, receiptFileName +".txt");
    }

    @Override
    public Receipt readReceipt() {
        return null;
    }

    @Override
    public int getReceiptCount() throws ClassNotFoundException, IOException {
      return (int) receiptCountRepository.readBin(receiptCountFileName);
    }
}
