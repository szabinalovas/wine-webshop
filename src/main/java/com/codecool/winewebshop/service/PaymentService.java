package com.codecool.winewebshop.service;

import com.codecool.winewebshop.dto.PaymentDto;
import com.codecool.winewebshop.dto.PaymentMapper;
import com.codecool.winewebshop.entity.Cart;
import com.codecool.winewebshop.entity.Payment;
import com.codecool.winewebshop.entity.paymentEnums.PaymentStatus;
import com.codecool.winewebshop.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    private final CartService cartService;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper, CartService cartService) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.cartService = cartService;
    }

    public PaymentDto addPayment(Long cartId, PaymentDto paymentDto) {
        Cart cart = cartService.findById(cartId);
        if (cart.getPayment() != null) {
            throw new IllegalArgumentException("Cart already paid!");
        }
        Payment payment = new Payment();
        cart.setPayment(payment);
        payment.setPaymentType(paymentDto.getPaymentType());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDate.now());
        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    public PaymentDto findPaymentByCartId(Long cartId) {
        Cart cart = cartService.findById(cartId);
        if(cart == null){
            return null;
        }
        return paymentMapper.toDto(paymentRepository.findByCart(cart));
    }

    public PaymentDto updatePayment(Long cartId, PaymentDto paymentDto) {
        Cart cart = cartService.findById(cartId);
        if(cart == null){
            return null;
        }
        paymentMapper.updatePaymentFromDto(paymentDto, cart.getPayment());
        return paymentMapper.toDto(paymentRepository.save(cart.getPayment()));
    }

    @Transactional
    public void deletePaymentByCartId(Long cartId) {
        Cart cart = cartService.findById(cartId);
        paymentRepository.deleteById(cart.getPayment().getId());
        cart.setPayment(null);
    }
}
