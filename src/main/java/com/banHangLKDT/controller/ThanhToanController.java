package com.banHangLKDT.controller;

import com.banHangLKDT.dto.request.ThanhToanRequestDTO;
import com.banHangLKDT.dto.response.ThanhToanResponseDTO;
import com.banHangLKDT.service.ThanhToanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ThanhToanController {
    @Autowired
    private ThanhToanService thanhToanService;

    @PostMapping("/thanhtoan")
    public ResponseEntity<?> thanhToanTest(@Valid @RequestBody List<ThanhToanRequestDTO> dtoList) {
        ThanhToanResponseDTO response = thanhToanService.thanhToan(dtoList);

        if (response == null) {
            return ResponseEntity.badRequest().body("Số lượng đặt hàng không được lớn hơn hàng tồn");
        }

        return ResponseEntity.ok(response);
    }

}
