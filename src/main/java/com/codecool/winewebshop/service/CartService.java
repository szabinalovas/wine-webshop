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
import java.util.List;

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
        cart.setCustomer(customer);
        if (cart.getProducts() != null && !cart.getProducts().isEmpty()) {
            cart.getProducts().add(product);
        } else {
            cart.setProducts(List.of(product));
        }
        cart.setTotal(cart.getTotal() + product.getPrice());
        product.setQuantityInStock(product.getQuantityInStock() - 1);
        return cartMapper.toDto(cartRepository.save(cart));
    }

    public CartDto findDtoByCustomer(Long customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null) {
            return null;
        }
        return cartMapper.toDto(cartRepository.findByCustomer(customer));
    }

    public Cart findByCustomer(Long customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        return cartRepository.findByCustomer(customer);
    }

    public Cart findById(Long id) {
        return cartRepository.findById(id).orElse(null);
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
