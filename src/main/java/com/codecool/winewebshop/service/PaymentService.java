package com.codecool.winewebshop.service;

import com.codecool.winewebshop.dto.PaymentDto;
import com.codecool.winewebshop.dto.PaymentMapper;
import com.codecool.winewebshop.entity.Payment;
import com.codecool.winewebshop.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    public PaymentDto addPayment(Payment payment) {
        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    public List<PaymentDto> findAllPayment() {
        return paymentMapper.toDto(paymentRepository.findAll());
    }

    public PaymentDto findPaymentById(Long id) {
        return paymentMapper.toDto(paymentRepository.findById(id).get());
    }

    public PaymentDto updatePayment(Long id, PaymentDto paymentDto) {
        Payment payment = paymentRepository.findById(id).get();
        paymentMapper.updatePaymentFromDto(paymentDto, payment);
        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    public void deletePaymentById(Long id) {
        paymentRepository.deleteById(id);
    }

}
