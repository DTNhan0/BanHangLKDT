package com.banHangLKDT.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductListResponseDTO {
    private ResponseStatus status;
    private List<ProductResponseDTO> data;
}
