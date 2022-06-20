package com.codecool.winewebshop.controller;

import com.codecool.winewebshop.dto.PaymentDto;
import com.codecool.winewebshop.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/payments")
@Slf4j
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
            log.error("Cart with id: " + cartId + " not found.");
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(paymentService.addPayment(cartId, paymentDto), HttpStatus.CREATED);
    }

    @GetMapping("/status/{cart_id}")
    public ResponseEntity<PaymentDto> findPaymentByCartId(@PathVariable("cart_id") Long cartId) {
        PaymentDto payment;
        try {
            payment = paymentService.findPaymentByCartId(cartId);

        } catch (NoSuchElementException e) {
            log.error("Cart with id: " + cartId + " not found.");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(payment);
    }

    @PutMapping("/{cart_id}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable("cart_id") Long cartId,
                                                    @Valid @RequestBody PaymentDto paymentDto,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getFieldErrors().toString());
            return ResponseEntity.badRequest().build();
        }

        PaymentDto payment;
        try {
            payment = paymentService.updatePayment(cartId, paymentDto);
        } catch (NoSuchElementException e) {
            log.error("Cart with id: " + cartId + " not found.");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(payment);
    }

    @DeleteMapping("/{cart_id}")
    public ResponseEntity<Void> deletePaymentById(@PathVariable("cart_id") Long cartId) {
        paymentService.deletePaymentByCartId(cartId);
        log.info("Payment with cartId: " + cartId + " was deleted.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
