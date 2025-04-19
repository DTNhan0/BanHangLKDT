package com.banHangLKDT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "receipt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdReceipt")
    private Integer idReceipt;

    @Column(name = "TotalPrice")
    private Float totalPrice;

    @Column(name = "Customer")
    private String customer;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Column(name = "UpdateAt", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss",
            timezone = "Asia/Ho_Chi_Minh"
    )
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL)
    private List<ReceiptDetail> receiptDetails = new ArrayList<>();

    // Thêm phương thức tính tổng
    public Float getTotalAllPrice() {
        return receiptDetails.stream()
                .map(ReceiptDetail::getTotalPrice)
                .reduce(0f, Float::sum);
    }

    @PrePersist
    protected void onCreate() {
        this.updateAt = LocalDateTime.now(); // Không cần TimeZone, lấy thời gian local [[9]]
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = LocalDateTime.now();
    }
}
