package com.codecool.winewebshop.repository;

import com.codecool.winewebshop.entity.Cart;
import com.codecool.winewebshop.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByCart(Cart cart);
}
