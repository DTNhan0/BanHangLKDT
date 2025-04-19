package com.banHangLKDT.controller;

import com.banHangLKDT.dto.request.ReceiptRequestDTO;
import com.banHangLKDT.dto.response.ReceiptDetailResponseDTO;
import com.banHangLKDT.dto.response.ReceiptResponseDTO;
import com.banHangLKDT.model.Receipt;
import com.banHangLKDT.service.receipt.ReceiptServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class ReceiptController {

    @Autowired
    private ReceiptServiceImpl receiptService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public ReceiptController(ReceiptServiceImpl receiptService) {
        this.receiptService = receiptService;
    }

    @Autowired
    public void setReceiptService(ReceiptServiceImpl receiptService) {
        this.receiptService = receiptService;
    }

    //CREATE
    @PostMapping("/receipt")
    public ResponseEntity<ReceiptResponseDTO> createReceipt(@Valid @RequestBody ReceiptRequestDTO dto){
        Receipt receipt = this.modelMapper.map(dto, Receipt.class);
        return ResponseEntity.ok(this.modelMapper.map(receiptService.createReceipt(receipt), ReceiptResponseDTO.class));
    }

    //READ
    @GetMapping("/receipts")
    public ResponseEntity<List<ReceiptResponseDTO>> getAllReceipt(){
        return ResponseEntity.ok(
                receiptService.getAllReceipt()
                        .stream().map(r -> this.modelMapper.map(r, ReceiptResponseDTO.class))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/receipt/{idReceipt}")
    public ResponseEntity<ReceiptResponseDTO> getReceipt(@PathVariable("idReceipt") int idReceipt){
        Receipt receipt = receiptService.getReceipt(idReceipt);

        if(receipt == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(this.modelMapper.map(receipt, ReceiptResponseDTO.class));
    }

    //UPDATE

    //DELETE

    //OTHER
    @GetMapping("/receipt/{receiptId}/detail")
    public ResponseEntity<ReceiptDetailResponseDTO> getReceipt(@PathVariable Integer receiptId) {
        ReceiptDetailResponseDTO responseDTO = receiptService.getReceiptWithDetails(receiptId);

        if(responseDTO == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(responseDTO);
    }
}
