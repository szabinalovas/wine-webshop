package com.codecool.winewebshop.controller;

import com.codecool.winewebshop.dto.PaymentDto;
import com.codecool.winewebshop.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
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
    @Operation(summary = "Add payment to cart")
    public ResponseEntity<PaymentDto> addPayment(@PathVariable("cart_id") Long cartId,
                                                 @Valid @RequestBody PaymentDto paymentDto,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Cart with id: " + cartId + " not found.");
            return ResponseEntity.badRequest().build();
        }

        PaymentDto paymentDto1;
        try {
            paymentDto1 = paymentService.addPayment(cartId, paymentDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        log.info("Payment added to cart");
        return new ResponseEntity<>(paymentDto1, HttpStatus.CREATED);
    }

    @GetMapping("/status/{cart_id}")
    @Operation(summary = "Get carts payment status")
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
    @Operation(summary = "Update payment by cart id")
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
        log.info("Payment updated");
        return ResponseEntity.ok(payment);
    }

    @DeleteMapping("/{cart_id}")
    @Operation(summary = "Delete payment by cart id")
    public ResponseEntity<Void> deletePaymentById(@PathVariable("cart_id") Long cartId) {
        try {
            paymentService.deletePaymentByCartId(cartId);
        } catch (Exception e) {
            log.error("Payment with id: " + cartId + " not found.");
            return ResponseEntity.notFound().build();
        }
        log.info("Payment with cartId: " + cartId + " was deleted.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
