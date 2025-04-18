package com.banHangLKDT.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotNull(message = "Product name is required")
    @Size(min = 1, max = 255, message = "Product name must be between 1 and 255 characters")
    private String productName;

    @Size(max = 255, message = "Description must be at most 255 characters")
    private String description;

    @Size(max = 255, message = "Image name must be at most 255 characters")
    private String imageName;

    @Size(max = 50, message = "Image type must be at most 50 characters")
    private String imageType;

    private byte[] imageData;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be 0 or greater")
    private Integer quantity;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Float price;
}

