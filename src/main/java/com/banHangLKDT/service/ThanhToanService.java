package com.banHangLKDT.service;

import com.banHangLKDT.dto.request.ReceiptRequestDTO;
import com.banHangLKDT.dto.request.ThanhToanRequestDTO;
import com.banHangLKDT.dto.response.ReceiptResponseDTO;
import com.banHangLKDT.dto.response.ResponseStatus;
import com.banHangLKDT.dto.response.ThanhToanResponseDTO;
import com.banHangLKDT.model.Product;
import com.banHangLKDT.model.Receipt;
import com.banHangLKDT.model.ReceiptDetail;
import com.banHangLKDT.service.product.ProductService;
import com.banHangLKDT.service.product.ProductServiceImpl;
import com.banHangLKDT.service.receipt.ReceiptServiceImpl;
import com.banHangLKDT.service.receiptDetail.ReceiptDetailServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThanhToanService {
    @Autowired
    ProductServiceImpl productService;

    @Autowired
    ReceiptDetailServiceImpl receiptDetailService;

    @Autowired
    ReceiptServiceImpl receiptService;

    @Autowired
    private ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager em;

    private void resetAutoIncrement() {
        // Thiết lập AUTO_INCREMENT về 1 (tức next = max(id)+1)
        em.createNativeQuery("ALTER TABLE receiptdetail AUTO_INCREMENT = 1;")
                .executeUpdate();
        em.createNativeQuery("ALTER TABLE receipt AUTO_INCREMENT = 1;")
                .executeUpdate();
    }

    @Transactional
    public ThanhToanResponseDTO thanhToan(ThanhToanRequestDTO dto){

        resetAutoIncrement();
//        if(checkQuantityPrice(dtoList))
//            return null;

        Float totalPrice = sumPrice(dto.getCart());

        Receipt receipt = new Receipt();
        receipt.setCustomer(dto.getCustomer());
        receipt.setPhoneNumber(dto.getPhoneNumber());
        receipt.setTotalPrice(totalPrice);

        //SAVE TO DB...
        receipt = receiptService.createReceipt(receipt);

        List<ReceiptDetail> receiptDetailList = new ArrayList<>();

        for (ThanhToanRequestDTO.ProductCartList ttd : dto.getCart()) {
            // Lấy product từ database
            Product product = productService.getProduct(ttd.getIdProduct());

            ReceiptDetail receiptDetail = new ReceiptDetail();
            receiptDetail.setReceipt(receipt); // Set toàn bộ đối tượng Receipt
            receiptDetail.setProduct(product); // Set toàn bộ đối tượng Product
            receiptDetail.setUnitPrice(ttd.getPrice());
            receiptDetail.setOrderQuantity(ttd.getQuantity());

            receiptDetailList.add(receiptDetail);
        }

        // SAVE RECEIPT DETAILS
        receiptDetailService.createReceiptDetail(receiptDetailList);

        ThanhToanResponseDTO thanhToanResponseDTO = new ThanhToanResponseDTO();

        thanhToanResponseDTO.setThongTinHoaDon(this.modelMapper.map(receipt, ReceiptResponseDTO.class));

        thanhToanResponseDTO.setDaThanhToanList(
                receiptDetailList.stream()
                        .map(rd -> this.modelMapper.map(rd, ThanhToanResponseDTO.DaThanhToan.class))
                        .collect(Collectors.toList())
        );

        thanhToanResponseDTO.setStatus(ResponseStatus.SUCCESS);

        return thanhToanResponseDTO;
    }

    public Float sumPrice(List<ThanhToanRequestDTO.ProductCartList> dtoList){
        return dtoList.stream()
                .map(dto -> dto.getPrice() * dto.getQuantity())
                .reduce(0f, Float::sum);
    }

    public boolean checkQuantityPrice(){
        return false;
    }
}
