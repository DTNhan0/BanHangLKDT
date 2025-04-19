package com.banHangLKDT.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "receiptdetail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdRD")
    private Integer idRd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdReceipt")
    private Receipt receipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdProduct")
    private Product product;

    @Column(name = "OrderQuantity")
    private Integer orderQuantity;

    @Column(name = "UnitPrice")
    private Float unitPrice;

    // Thêm trường tính toán
    public Float getTotalPrice() {
        return orderQuantity * unitPrice;
    }
}