package com.codecool.winewebshop.controller;

import com.codecool.winewebshop.dto.PaymentDto;
import com.codecool.winewebshop.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public PaymentDto addPayment(@RequestBody PaymentDto paymentDto) {
        return paymentService.addPayment(paymentDto);
    }

    @GetMapping
    public List<PaymentDto> findAllPayment() {
        return paymentService.findAllPayment();
    }

    @GetMapping("/{id}")
    public PaymentDto findPaymentById(@PathVariable("id") Long id) {
        return paymentService.findPaymentById(id);
    }

    @PutMapping("/{id}")
    public PaymentDto updatePayment(@PathVariable("id") Long id, @RequestBody PaymentDto paymentDto) {
        return paymentService.updatePayment(id, paymentDto);
    }

    @DeleteMapping("/{id}")
    public void deletePaymentById(@PathVariable("id") Long id) {
        paymentService.deletePaymentById(id);
    }
}
