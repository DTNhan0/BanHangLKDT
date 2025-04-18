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

    @Column(name = "OrderQuantity")
    private Integer orderQuantity;

    @Column(name = "UnitPrice")
    private Float unitPrice;

    @Column(name = "IdProduct")
    private Integer idProduct;

    @Column(name = "IdReceipt")
    private Integer idReceipt;

}