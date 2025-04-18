package com.banHangLKDT.service.receipt;

import com.banHangLKDT.dto.request.ReceiptRequestDTO;
import com.banHangLKDT.model.Receipt;
import com.banHangLKDT.repository.ReceiptRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptServiceImpl implements ReceiptService{

    @Autowired
    private ReceiptRepo receiptRepo;

    @Autowired
    public ReceiptServiceImpl(ReceiptRepo receiptRepo) {
        this.receiptRepo = receiptRepo;
    }

    @Autowired
    private ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager em;

    private void resetAutoIncrement() {
        // Thiết lập AUTO_INCREMENT về 1 (tức next = max(id)+1)
        em.createNativeQuery("ALTER TABLE receipt AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    @Autowired
    public void setReceiptRepo(ReceiptRepo receiptRepo) {
        this.receiptRepo = receiptRepo;
    }

    //CREATE
    @Transactional
    @Override
    public Receipt createReceipt(ReceiptRequestDTO dto) {
        resetAutoIncrement();
        return receiptRepo.save(this.modelMapper.map(dto, Receipt.class));
    }

    //READ
    @Override
    public List<Receipt> getAllReceipt() {
        return receiptRepo.findAll();
    }

    @Override
    public Receipt getReceipt(int idReceipt) {
        return receiptRepo.findById(idReceipt).orElse(null);
    }

    //UPDATE
    @Override
    public Receipt updateReceipt(int idReceipt, ReceiptRequestDTO dto) {
        return null;
    }

    //DELETE
    @Override
    public void deleteReceipt(int idReceipt) {

    }
}
