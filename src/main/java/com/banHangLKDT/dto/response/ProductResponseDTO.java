package com.banHangLKDT.dto.response;

import com.banHangLKDT.model.ReceiptDetail;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponseDTO {
    private Integer idProduct;

    private String productName;

    private String description;

    private String imageName;

    private String imageType;

    private byte[] imageData;

    private Integer quantity;

    private Float price;
}
