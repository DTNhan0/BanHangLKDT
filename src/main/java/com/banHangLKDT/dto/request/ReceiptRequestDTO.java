package com.banHangLKDT.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptRequestDTO {
    @NotNull(message = "totalPrice is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "totalPrice must be greater than 0")
    private Float totalPrice;

    @NotNull(message = "customer is required")
    @Size(max = 50, message = "customer must be at most 50 characters")
    private String customer;

    @NotNull(message = "phoneNumber is required")
    @Size(min = 10, max = 10, message = "phoneNumber must be 10 characters")
    private String phoneNumber;
}
