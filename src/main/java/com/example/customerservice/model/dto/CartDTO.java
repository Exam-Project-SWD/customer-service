package com.example.customerservice.model.dto;

import com.example.customerservice.model.entity.Cart;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartDTO {
    private int customerId;
    private int restaurantId;
    private List<CartItemDTO> items = new ArrayList<>();
    private double totalPrice;
    private boolean withDelivery;

    public CartDTO(Cart cart) {
        this.customerId = cart.getCustomerId();
        this.restaurantId = cart.getRestaurantId();
//        this.items = cart.getItems()
//                .stream()
//                .map(item -> CartItemDTO.builder()
//                        .menuItemId(item.getMenuItemId())
//                        .quantity(item.getQuantity())
//                        .build())
//                .toList();
        this.totalPrice = cart.getTotalPrice();
    }
}