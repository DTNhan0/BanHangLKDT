package com.banHangLKDT.repository;

import com.banHangLKDT.model.ReceiptDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptDetailRepo extends JpaRepository<ReceiptDetail, Integer> {
}
