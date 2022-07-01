package com.codecool.winewebshop.dto;

import com.codecool.winewebshop.entity.paymentEnums.EnumNamePattern;
import com.codecool.winewebshop.entity.paymentEnums.PaymentStatus;
import com.codecool.winewebshop.entity.paymentEnums.PaymentType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class PaymentDto {

    private Long id;
    @EnumNamePattern(regexp = "CASH_ON_DELIVERY|CARD_PAYMENT|APPLE_PAY|GOOGLE_PAY")
    @NotNull
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private LocalDate paymentDate;
}
