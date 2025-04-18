package com.banHangLKDT.service.product;

import com.banHangLKDT.dto.request.ProductRequestDTO;
import com.banHangLKDT.dto.response.ProductResponseDTO;
import com.banHangLKDT.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductRequestDTO dto);
    List<Product> getAllProduct();
    Product getProduct(int idProduct);
    Product updateProduct(int idProduct, ProductRequestDTO dto);
    void deleteProduct(int idProduct);
}
