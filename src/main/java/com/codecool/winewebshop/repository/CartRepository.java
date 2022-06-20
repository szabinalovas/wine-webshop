package com.codecool.winewebshop.repository;

import com.codecool.winewebshop.entity.Cart;
import com.codecool.winewebshop.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByCustomer(Customer customer);

    void deleteByCustomer(Customer customer);
}
