package com.codecool.winewebshop.dto;

import com.codecool.winewebshop.entity.Cart;
import com.codecool.winewebshop.entity.paymentEnums.PaymentStatus;
import com.codecool.winewebshop.entity.paymentEnums.PaymentType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PaymentDto {

    private Long id;
    private CartDto cart;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private LocalDate paymentDate;
}
