package com.codecool.winewebshop.controller;

import com.codecool.winewebshop.dto.PaymentDto;
import com.codecool.winewebshop.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{cart_id}")
    public PaymentDto addPayment(@PathVariable("cart_id") Long cartId, @RequestBody PaymentDto paymentDto) {
        return paymentService.addPayment(cartId, paymentDto);
    }

    @GetMapping("/status/{cart_id}")
    public PaymentDto findPaymentByCartId(@PathVariable("cart_id") Long cartId) {
        return paymentService.findPaymentByCartId(cartId);
    }

    @PutMapping("/{cart_id}")
    public PaymentDto updatePayment(@PathVariable("cart_id") Long cartId, @RequestBody PaymentDto paymentDto) {
        return paymentService.updatePayment(cartId, paymentDto);
    }

    @DeleteMapping("/{cart_id}")
    public void deletePaymentById(@PathVariable("cart_id") Long cartId) {
        paymentService.deletePaymentByCartId(cartId);
    }
}
