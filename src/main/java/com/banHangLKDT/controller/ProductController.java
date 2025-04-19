package com.banHangLKDT.controller;

import com.banHangLKDT.dto.request.ProductRequestDTO;
import com.banHangLKDT.dto.response.ProductListResponseDTO;
import com.banHangLKDT.dto.response.ProductResponseDTO;
import com.banHangLKDT.dto.response.ResponseStatus;
import com.banHangLKDT.model.Product;
import com.banHangLKDT.service.product.ProductServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @Autowired
    public void setProductService(ProductServiceImpl productService) {
        this.productService = productService;
    }

    //CREATE
    @PostMapping("/product")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO dto) {
        Product product = this.modelMapper.map(dto, Product.class);
        return ResponseEntity.ok(this.modelMapper.map(productService.createProduct(product), ProductResponseDTO.class));
    }

    //READ
    @GetMapping("/products")
    public ResponseEntity<ProductListResponseDTO> getAllProduct() {
        List <ProductResponseDTO> productResponseDTOList = productService.getAllProduct().stream()
                .map(p -> this.modelMapper.map(p, ProductResponseDTO.class))
                .toList();
        ProductListResponseDTO productListResponseDTO = new ProductListResponseDTO(ResponseStatus.SUCCESS, productResponseDTOList);
        return ResponseEntity.ok(productListResponseDTO);
    }

    @GetMapping("/product/{idProduct}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable int idProduct) {
        Product product = productService.getProduct(idProduct);

        if(product == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(this.modelMapper.map(product, ProductResponseDTO.class));
    }

    //UPDATE
    @PutMapping("/product/{idProduct}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable("idProduct") int idProduct,
            @Valid @RequestBody ProductRequestDTO dto) {
        Product product = this.modelMapper.map(dto, Product.class);
        return ResponseEntity.ok(
                this.modelMapper.map(productService.updateProduct(idProduct, product), ProductResponseDTO.class)
        );
    }

    //DELETE
    @DeleteMapping("/product/{idProduct}")
    public void deleteProduct(@PathVariable("idProduct") int idProduct) {
        productService.deleteProduct(idProduct);
    }

    //OTHER
    @GetMapping("/product/{idProduct}/image")
    public Product getProductImage(@PathVariable int idProduct) {
        return null;
    }
}
