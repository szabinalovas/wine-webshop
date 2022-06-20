package com.codecool.winewebshop.dto;

import com.codecool.winewebshop.entity.paymentEnums.EnumNamePattern;
import com.codecool.winewebshop.entity.paymentEnums.PaymentStatus;
import com.codecool.winewebshop.entity.paymentEnums.PaymentType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class PaymentDto {

    private Long id;
    @EnumNamePattern(regexp = "CASH_ON_DELIVERY|CARD_PAYMENT|APPLE_PAY|GOOGLE_PAY")
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private LocalDate paymentDate;
}
