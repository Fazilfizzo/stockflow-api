package com.fizoind.stockflow_api.cart.service.impl;

import com.fizoind.stockflow_api.authentication.CustomUserDetails;
import com.fizoind.stockflow_api.cart.dto.AddToCartRequest;
import com.fizoind.stockflow_api.cart.dto.CartGetResponse;
import com.fizoind.stockflow_api.cart.dto.CartResponse;
import com.fizoind.stockflow_api.cart.dto.UpdateCartRequest;
import com.fizoind.stockflow_api.cart.entity.Cart;
import com.fizoind.stockflow_api.cart.mapper.CartMapper;
import com.fizoind.stockflow_api.cart.repository.CartRepository;
import com.fizoind.stockflow_api.cart.service.CartService;
import com.fizoind.stockflow_api.cartItem.entity.CartItem;
import com.fizoind.stockflow_api.cartItem.exception.CartItemException;
import com.fizoind.stockflow_api.cartItem.repository.CartItemRepository;
import com.fizoind.stockflow_api.customer.entity.Customer;
import com.fizoind.stockflow_api.customer.exception.CustomerNotFoundException;
import com.fizoind.stockflow_api.customer.repository.CustomerRepository;
import com.fizoind.stockflow_api.exception.QuantityException;
import com.fizoind.stockflow_api.product.entity.Product;
import com.fizoind.stockflow_api.product.exception.ProductNotFoundException;
import com.fizoind.stockflow_api.product.repository.ProductRepository;
import com.fizoind.stockflow_api.stockmovement.exception.InsufficientStockException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public CartGetResponse addToCart(AddToCartRequest addToCartRequest) {
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

        if (product.getStockQuantity() <= 0) {
            throw new InsufficientStockException(product.getId());
        }

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product).orElse(null);

        if (cartItem == null) {
            if (cart.getItems() == null) {
                cart.setItems(new ArrayList<>());
            }
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(addToCartRequest.quantity());

            cart.getItems().add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + addToCartRequest.quantity());
        }

        cartRepository.save(cart);

        return CartMapper.toGetCartResponse(cart);
    }

    @Override
    public CartGetResponse getCart() {
        Long customerId = ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser().getId();

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));

        Cart customerCart = cartRepository.findByCustomer(customer).orElseThrow(() -> new RuntimeException("Cart not found of customer"));

        return CartMapper.toGetCartResponse(customerCart);

    }

    @Override
    public CartGetResponse updateCartQuantity(Long cartItemId, UpdateCartRequest updateCartRequest) {

        Long customerId = ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser().getId();

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("CartItem does not exist"));

        Product product = cartItem.getProduct();

//        if (updateCartRequest.quantity() <= 0) {
//
//        }

        if (updateCartRequest.quantity() > product.getStockQuantity()) {
            throw new InsufficientStockException(product.getId());
        }

        if (updateCartRequest.quantity() < 0) {
            throw new QuantityException("Quantity must be greater than zero");
        }

        cartItem.setQuantity(updateCartRequest.quantity());
        cartItemRepository.save(cartItem);

        return CartMapper.toGetCartResponse(cartItem.getCart());
    }


    @Override
    public CartGetResponse removeItem(Long cartItemId) {
        Long customerId = ((CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUser().getId();

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new CartItemException("Cart item not found"));

        Cart cart = cartItem.getCart();

        cart.getItems().remove(cartItem);

        cartItemRepository.delete(cartItem);

        return CartMapper.toGetCartResponse(cart);
    }

}