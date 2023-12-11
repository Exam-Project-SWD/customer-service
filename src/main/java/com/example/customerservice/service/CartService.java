package com.example.customerservice.service;

import com.example.customerservice.listener.KafkaListeners;
import com.example.customerservice.model.dto.CartDTO;
import com.example.customerservice.model.entity.Cart;
import com.example.customerservice.model.entity.CartItem;
import com.example.customerservice.model.entity.Item;
import com.example.customerservice.repository.CartItemRepository;
import com.example.customerservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final KafkaListeners kafkaListener;

    public CartDTO addItemToCart(int customerId, Cart cart) {
        cart.setCustomerId(customerId);
        Cart newCustomerCart = findCartForCustomer(cart);

        //TODO: calc total price
        newCustomerCart.setTotalPrice(10.5);

        newCustomerCart.getItems().clear();

        for (CartItem item : cart.getItems()) {
            newCustomerCart.getItems().add(item);
            cartItemRepository.save(item);
        }

        CartDTO cartDTO = new CartDTO(cartRepository.save(newCustomerCart));

        return cartDTO;
    }

    private Cart findCartForCustomer(Cart cart) {
        return cartRepository.findById(cart.getCustomerId()).orElseGet(() -> cartRepository.save(cart));
    }
}