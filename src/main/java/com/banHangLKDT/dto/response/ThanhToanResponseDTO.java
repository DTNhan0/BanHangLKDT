package com.banHangLKDT.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ThanhToanResponseDTO {
    private ResponseStatus status;
    private String message;
    private List<DaThanhToan> daThanhToanList;
    private ReceiptResponseDTO thongTinHoaDon;

    @Data
    public static class DaThanhToan {
        private Integer idProduct;
        private Integer quantity;
        private Float unitPrice;    // Đổi tên thành unitPrice
        private Float totalPrice;
    }
}
