package com.banHangLKDT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdProduct")
    private Integer idProduct;

    @Column(name = "ProductName")
    private String productName;

    @Column(name = "Description")
    private String description;

    @Column(name = "ImageName")
    private String imageName;

    @Column(name = "ImageType")
    private String imageType;

    @Lob
    @Column(name = "ImageData")
    private byte[] imageData;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Price")
    private Float price;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(idProduct, product.idProduct) && Objects.equals(productName, product.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, productName);
    }
}
