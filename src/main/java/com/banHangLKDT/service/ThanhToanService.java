package com.banHangLKDT.service;

import com.banHangLKDT.dto.request.ReceiptRequestDTO;
import com.banHangLKDT.dto.request.ThanhToanRequestDTO;
import com.banHangLKDT.dto.response.ReceiptResponseDTO;
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

    public ThanhToanResponseDTO thanhToan(List<ThanhToanRequestDTO> dtoList){

        if(checkQuantityPrice(dtoList))
            return null;

        Float totalPrice = sumPrice(dtoList);
        String customer = dtoList.getFirst().getCustomer();
        String phoneNumber = dtoList.getFirst().getPhoneNumber();

        Receipt receipt = new Receipt();
        receipt.setCustomer(customer);
        receipt.setPhoneNumber(phoneNumber);
        receipt.setTotalPrice(totalPrice);

        //SAVE TO DB...
        receipt = receiptService.createReceipt(this.modelMapper.map(receipt, ReceiptRequestDTO.class));

        List<ReceiptDetail> receiptDetailList = new ArrayList<ReceiptDetail>();

        for(ThanhToanRequestDTO ttd : dtoList){
            ReceiptDetail receiptDetail = new ReceiptDetail();
            receiptDetail.setIdProduct(ttd.getIdProduct());
            receiptDetail.setIdReceipt(receipt.getIdReceipt());
            receiptDetail.setUnitPrice(ttd.getPrice());
            receiptDetail.setOrderQuantity(ttd.getQuantity());
            receiptDetailList.add(receiptDetail);
        }

        //SAVE TO DB...
        receiptDetailService.createReceiptDetail(receiptDetailList);

        ThanhToanResponseDTO thanhToanResponseDTO = new ThanhToanResponseDTO();

        thanhToanResponseDTO.setReceiptResponseDTO(this.modelMapper.map(receipt, ReceiptResponseDTO.class));

        thanhToanResponseDTO.setDaThanhToanList(
                receiptDetailList.stream()
                        .map(rd -> this.modelMapper.map(rd, ThanhToanResponseDTO.DaThanhToan.class))
                        .collect(Collectors.toList())
        );

        return thanhToanResponseDTO;
    }

    public Float sumPrice(List<ThanhToanRequestDTO> dtoList){
        return dtoList.stream()
                .map(dto -> dto.getPrice() * dto.getQuantity())
                .reduce(0f, Float::sum);
    }

    public boolean checkQuantityPrice(List<ThanhToanRequestDTO> dtoList){
        for(ThanhToanRequestDTO ttd : dtoList){

            Integer quantityDatHang =  ttd.getQuantity();

            Product product = productService.getProduct(ttd.getIdProduct());

            if(quantityDatHang > product.getQuantity())
                return false;
        }

        return true;
    }
}
