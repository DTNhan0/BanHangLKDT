package com.banHangLKDT.service.receipt;

import com.banHangLKDT.dto.request.ReceiptRequestDTO;
import com.banHangLKDT.dto.response.ReceiptDetailResponseDTO;
import com.banHangLKDT.dto.response.ResponseStatus;
import com.banHangLKDT.model.Receipt;
import com.banHangLKDT.model.ReceiptDetail;
import com.banHangLKDT.repository.ReceiptRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Receipt createReceipt(Receipt receipt) {
        resetAutoIncrement();
        return receiptRepo.save(receipt);
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
    public Receipt updateReceipt(int idReceipt, Receipt receipt) {
        return null;
    }

    //DELETE
    @Override
    public void deleteReceipt(int idReceipt) {

    }

    //OTHER
    public ReceiptDetailResponseDTO getReceiptWithDetails(Integer receiptId) {
        Optional<Receipt> receiptOptional = receiptRepo.findByIdWithDetails(receiptId);

        if (!receiptOptional.isPresent()) {
            return createErrorResponse("Không tìm thấy hóa đơn");
        }

        return mapToResponse(receiptOptional.get());
    }

    private ReceiptDetailResponseDTO createErrorResponse(String message) {
        ReceiptDetailResponseDTO response = new ReceiptDetailResponseDTO();
        response.setStatus(ResponseStatus.FAILED);
        response.setMessage(message);
        return response;
    }

    private ReceiptDetailResponseDTO mapToResponse(Receipt receipt) {
        ReceiptDetailResponseDTO response = new ReceiptDetailResponseDTO();
        response.setStatus(ResponseStatus.SUCCESS);

        ReceiptDetailResponseDTO.ReceiptData data = new ReceiptDetailResponseDTO.ReceiptData();
        modelMapper.map(receipt, data);

        data.setCart(receipt.getReceiptDetails().stream()
                .map(this::convertToCartItem)
                .toList());

        data.setTotalAllPrice(receipt.getTotalAllPrice());

        response.setData(data);
        return response;
    }

    private ReceiptDetailResponseDTO.CartItem convertToCartItem(ReceiptDetail detail) {
        ReceiptDetailResponseDTO.CartItem item = new ReceiptDetailResponseDTO.CartItem();
        item.setProductId(detail.getProduct().getIdProduct());
        item.setProductName(detail.getProduct().getProductName());
        item.setOrderQuantity(detail.getOrderQuantity());
        item.setUnitPrice(detail.getUnitPrice());
        item.setTotalPrice(detail.getTotalPrice());
        return item;
    }
}
