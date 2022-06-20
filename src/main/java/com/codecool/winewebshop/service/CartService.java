package com.codecool.winewebshop.service;

import com.codecool.winewebshop.dto.CartDto;
import com.codecool.winewebshop.dto.CartMapper;
import com.codecool.winewebshop.dto.ProductMapper;
import com.codecool.winewebshop.entity.Cart;
import com.codecool.winewebshop.entity.Customer;
import com.codecool.winewebshop.entity.Product;
import com.codecool.winewebshop.repository.CartRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CustomerService customerService;
    private final ProductService productService;
    private final ProductMapper productMapper;

    public CartService(CartRepository cartRepository, CartMapper cartMapper, CustomerService customerService, ProductService productService, ProductMapper productMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.customerService = customerService;
        this.productService = productService;
        this.productMapper = productMapper;
    }

    public CartDto addToCart(Long customerId, Long productId) {
        Customer customer = customerService.findCustomerById(customerId);
        Cart cart = customer.getCart() == null ? new Cart() : customer.getCart();
        Product product = productMapper.toEntity(productService.findProductById(productId));
        product.setQuantityInStock(product.getQuantityInStock() - 1);
        cart.setCustomer(customer);
        List<Product> products;
        if (cart.getProducts() != null && !cart.getProducts().isEmpty()) {
            products = cart.getProducts();
        } else {
            products = new ArrayList<>();
        }
        products.add(product);
        cart.setProducts(products);
        cart.setTotal(cart.getTotal() + product.getPrice());
        return cartMapper.toDto(cartRepository.save(cart));
    }

    public CartDto findDtoByCustomer(Long customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        Optional<Cart> cart = cartRepository.findByCustomer(customer);
        if (cart.isEmpty()) {
            throw new NoSuchElementException();
        }
        return cartMapper.toDto(cart.get());
    }

    public Cart findByCustomer(Long customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        Optional<Cart> cart = cartRepository.findByCustomer(customer);
        if (cart.isEmpty()) {
            throw new NoSuchElementException();
        }
        return cart.get();
    }

    public Cart findById(Long id) {
        return cartRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void deleteProductFromCustomerCart(Long customerId, Long productId) {
        Cart cart = findByCustomer(customerId);
        Product product = cart.getProducts().stream().filter(p -> p.getId().equals(productId)).findFirst().orElseThrow();
        productService.findProductById(productId);
        product.setQuantityInStock(product.getQuantityInStock() + 1);
        cart.setTotal(cart.getTotal() - product.getPrice());
        cart.getProducts().remove(product);
    }

    @Transactional
    public void deleteByCustomer(Long customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        cartRepository.deleteByCustomer(customer);
    }
}
