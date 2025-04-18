package com.banHangLKDT.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ThanhToanRequestDTO {

    @NotNull
    private String customer;

    @NotNull
    private String phoneNumber;

    @NotNull
    private Integer idProduct;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    @Min(1)
    private Float price;
}
