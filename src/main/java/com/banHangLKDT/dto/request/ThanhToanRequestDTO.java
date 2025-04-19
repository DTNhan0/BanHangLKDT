package com.banHangLKDT.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class ThanhToanRequestDTO {

    @NotNull
    private String customer;

    @NotNull
    private String phoneNumber;

    @NotNull
    private List<ProductCartList> cart;

    @Data
    public static class ProductCartList {
        @NotNull
        private Integer idProduct;

        @NotNull
        @Min(1)
        private Integer quantity;

        @NotNull
        @Min(1)
        private Float price;
    }

}
