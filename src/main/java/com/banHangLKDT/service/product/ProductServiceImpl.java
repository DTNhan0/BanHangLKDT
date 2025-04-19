package com.banHangLKDT.service.product;

import com.banHangLKDT.model.Product;
import com.banHangLKDT.repository.ProductRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager em;

    private void resetAutoIncrement() {
        // Thiết lập AUTO_INCREMENT về 1 (tức next = max(id)+1)
        em.createNativeQuery("ALTER TABLE product AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    //CREATE
    @Override
    @Transactional
    public Product createProduct(Product product) {
        resetAutoIncrement();
        return productRepo.save(product);
    }

    //READ
    @Override
    public List<Product> getAllProduct() {
        return productRepo.findAll();
    }

    @Override
    public Product getProduct(int idProduct) {
        return productRepo.findById(idProduct).orElse(null);
    }

    //UPDATE
    @Override
    @Transactional
    public Product updateProduct(int idProduct, Product product) {
        resetAutoIncrement();

        Product oldProduct = productRepo.findById(idProduct)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thông tin với ID: " + idProduct));

        // Kiểm tra CIC đã tồn tại (và khác cái hiện tại)
        if (!oldProduct.getProductName().equals(product.getProductName()) &&
                productRepo.existsByProductName(product.getProductName())) {
            throw new RuntimeException("Tên product đã tồn tại");
        }

        oldProduct.setProductName(product.getProductName());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setQuantity(product.getQuantity());

        return productRepo.save(oldProduct);
    }

    //DELETE
    @Override
    @Transactional
    public void deleteProduct(int idProduct) {
        Product product = productRepo.findById(idProduct)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thông tin với ID: " + idProduct));

        resetAutoIncrement();
        productRepo.deleteById(product.getIdProduct());
    }

    //OTHER
}
