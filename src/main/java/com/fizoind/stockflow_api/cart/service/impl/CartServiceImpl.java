package com.fizoind.stockflow_api.cart.service.impl;

import com.fizoind.stockflow_api.authentication.CustomUserDetails;
import com.fizoind.stockflow_api.cart.dto.AddToCartRequest;
import com.fizoind.stockflow_api.cart.dto.CartResponse;
import com.fizoind.stockflow_api.cart.entity.Cart;
import com.fizoind.stockflow_api.cart.mapper.CartMapper;
import com.fizoind.stockflow_api.cart.repository.CartRepository;
import com.fizoind.stockflow_api.cart.service.CartService;
import com.fizoind.stockflow_api.cartItem.entity.CartItem;
import com.fizoind.stockflow_api.cartItem.repository.CartItemRepository;
import com.fizoind.stockflow_api.customer.entity.Customer;
import com.fizoind.stockflow_api.customer.exception.CustomerNotFoundException;
import com.fizoind.stockflow_api.customer.repository.CustomerRepository;
import com.fizoind.stockflow_api.product.entity.Product;
import com.fizoind.stockflow_api.product.exception.ProductNotFoundException;
import com.fizoind.stockflow_api.product.repository.ProductRepository;
import com.fizoind.stockflow_api.stockmovement.exception.InsufficientStockException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, CustomerRepository customerRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartResponse addToCart(Long customerID, AddToCartRequest addToCartRequest) {
        Long customerId = ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser().getId();

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));

        Cart cart = cartRepository.findByCustomer(customer).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setCustomer(customer);
            return cartRepository.save(newCart);
        });

        Product product = productRepository.findById(addToCartRequest.productId()).orElseThrow(() -> new ProductNotFoundException(addToCartRequest.productId()));

        if (addToCartRequest.quantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product).orElse(null);

        if (cartItem == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(addToCartRequest.quantity());

            cart.getItems().add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + addToCartRequest.quantity());
        }

        cartRepository.save(cart);

        return CartMapper.toCartResponse(cart);
    }


}