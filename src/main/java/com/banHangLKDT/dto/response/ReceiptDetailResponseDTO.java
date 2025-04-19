package com.banHangLKDT.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReceiptDetailResponseDTO {
    private ResponseStatus status;
    private ReceiptData data;
    private String message;

    @Data
    public static class ReceiptData {
        private Integer receiptId;
        private String customer;
        private String phone;
        private List<CartItem> cart;
        private LocalDateTime updateAt;
        private Float totalAllPrice;
    }

    @Data
    public static class CartItem {
        private Integer productId;
        private String productName;
        private Integer orderQuantity;
        private Float unitPrice;
        private Float totalPrice;
    }
}
