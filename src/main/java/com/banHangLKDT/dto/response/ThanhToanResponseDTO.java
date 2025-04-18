package com.banHangLKDT.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ThanhToanResponseDTO {
    private List<DaThanhToan> daThanhToanList;
    private ReceiptResponseDTO receiptResponseDTO;

    @Data
    public static class DaThanhToan {
        private Integer idProduct;
        private Integer quantity;
        private Float price;
    }
}
