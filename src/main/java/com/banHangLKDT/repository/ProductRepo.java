package com.banHangLKDT.repository;

import com.banHangLKDT.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    boolean existsByProductName(String productName);
}
