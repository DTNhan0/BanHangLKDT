package com.banHangLKDT.service.receipt;

import com.banHangLKDT.dto.request.ReceiptRequestDTO;
import com.banHangLKDT.model.Receipt;

import java.util.List;

public interface ReceiptService {
    Receipt createReceipt(Receipt receipt);
    List<Receipt> getAllReceipt();
    Receipt getReceipt(int idReceipt);
    Receipt updateReceipt(int idReceipt, Receipt receipt);
    void deleteReceipt(int idReceipt);
}
