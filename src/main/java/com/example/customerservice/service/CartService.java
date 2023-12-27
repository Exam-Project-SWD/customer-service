package com.example.customerservice.service;

import com.example.customerservice.model.dto.CartDTO;
import com.example.customerservice.model.entity.Cart;
import com.example.customerservice.model.entity.CartItem;
import com.example.customerservice.model.entity.Item;
import com.example.customerservice.repository.CartItemRepository;
import com.example.customerservice.repository.CartRepository;
import com.example.customerservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final KafkaService kafkaService;

    public CartDTO addItemToCart(int customerId, Cart cart) {
        cart.setCustomerId(customerId);
        Cart newCustomerCart = findCartForCustomer(cart);

        int totalPrice = 0;
        for(CartItem item: cart.getItems()) {
            Optional<Item> newItem = itemRepository.findById(item.getId());
            double price = newItem.get().getPrice();
            totalPrice += (price * item.getQuantity());
        }
        newCustomerCart.setTotalPrice(totalPrice);

        newCustomerCart.setItems(cartItemRepository.saveAll(cart.getItems()));

        CartDTO cartDTO = new CartDTO(cartRepository.save(newCustomerCart));

        return cartDTO;
    }


    //TODO: This should be done by some kind of payment service insted
    public void payment(int customerId, int restaurantId) {
        kafkaService.sendCart(customerId, restaurantId);
    }

    private Cart findCartForCustomer(Cart cart) {
        return cartRepository.findById(cart.getCustomerId()).orElseGet(() -> cartRepository.save(cart));
    }
}