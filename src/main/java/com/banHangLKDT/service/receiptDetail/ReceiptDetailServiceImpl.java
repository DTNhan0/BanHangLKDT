package com.banHangLKDT.service.receiptDetail;

import com.banHangLKDT.model.ReceiptDetail;
import com.banHangLKDT.repository.ReceiptDetailRepo;
import com.banHangLKDT.repository.ReceiptRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptDetailServiceImpl implements ReceiptDetailService{

    @Autowired
    private ReceiptDetailRepo receiptDetailRepo;

    @Autowired
    private ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager em;

    private void resetAutoIncrement() {
        // Thiết lập AUTO_INCREMENT về 1 (tức next = max(id)+1)
        em.createNativeQuery("ALTER TABLE receiptdetail AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    @Transactional
    @Override
    public void createReceiptDetail(List<ReceiptDetail> receiptDetail) {
        resetAutoIncrement();
        receiptDetailRepo.saveAll(receiptDetail);
    }
}
