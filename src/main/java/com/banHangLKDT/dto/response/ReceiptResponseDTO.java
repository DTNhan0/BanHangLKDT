package com.banHangLKDT.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReceiptResponseDTO {

    private Integer idReceipt;

    private Float totalPrice;

    private String customer;

    private String phoneNumber;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss",
            timezone = "Asia/Ho_Chi_Minh"
    )
    private LocalDateTime updateAt;
}
