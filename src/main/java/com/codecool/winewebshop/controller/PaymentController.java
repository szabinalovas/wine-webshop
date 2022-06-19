package com.codecool.winewebshop.controller;

import com.codecool.winewebshop.dto.PaymentDto;
import com.codecool.winewebshop.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{cart_id}")
    public ResponseEntity<PaymentDto> addPayment(@PathVariable("cart_id") Long cartId,
                                                 @Valid @RequestBody PaymentDto paymentDto,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(paymentService.addPayment(cartId, paymentDto), HttpStatus.CREATED);
    }

    @GetMapping("/status/{cart_id}")
    public ResponseEntity<PaymentDto> findPaymentByCartId(@PathVariable("cart_id") Long cartId) {
        PaymentDto payment = paymentService.findPaymentByCartId(cartId);
        if (payment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/{cart_id}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable("cart_id") Long cartId,
                                                    @Valid @RequestBody PaymentDto paymentDto,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        PaymentDto payment = paymentService.updatePayment(cartId, paymentDto);
        if (payment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(payment);
    }

    @DeleteMapping("/{cart_id}")
    public ResponseEntity<Void> deletePaymentById(@PathVariable("cart_id") Long cartId) {
        paymentService.deletePaymentByCartId(cartId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
