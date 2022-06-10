package com.codecool.winewebshop.dto;

import com.codecool.winewebshop.entity.Payment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDto toDto(Payment payment);

    Payment toEntity(PaymentDto paymentDto);

    List<PaymentDto> toDto(List<Payment> payments);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePaymentFromDto(PaymentDto dto, @MappingTarget Payment payment);
}
