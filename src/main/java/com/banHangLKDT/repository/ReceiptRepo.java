package com.banHangLKDT.repository;

import com.banHangLKDT.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceiptRepo extends JpaRepository<Receipt, Integer> {
    @Query("SELECT r FROM Receipt r LEFT JOIN FETCH r.receiptDetails rd LEFT JOIN FETCH rd.product WHERE r.idReceipt = :id")
    Optional<Receipt> findByIdWithDetails(@Param("id") Integer id);
}
