package com.codecool.winewebshop.service;

import com.codecool.winewebshop.dto.CartDto;
import com.codecool.winewebshop.dto.CartMapper;
import com.codecool.winewebshop.dto.ProductDto;
import com.codecool.winewebshop.dto.ProductMapper;
import com.codecool.winewebshop.entity.Cart;
import com.codecool.winewebshop.entity.Customer;
import com.codecool.winewebshop.entity.Product;
import com.codecool.winewebshop.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        return cartMapper.toDto(cartRepository.save(cart));
    }

    public CartDto findByCustomer(Long customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        return cartMapper.toDto(cartRepository.findByCustomer(customer));
    }

    public void deleteProductFromCustomerCart(Long customerId, Long productId) {
        CartDto cartDto = findByCustomer(customerId);
        List<ProductDto> products = cartDto.getProducts();
        products.removeIf(product -> Objects.equals(product.getId(), productId));
    }

    public void deleteByCustomer(Long customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        cartRepository.deleteByCustomer(customer);
    }


}
