package com.example.customerservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @Column(name = "customer_id", unique = true, nullable = false)
    private int customerId;
    @Column(name = "restaurant_id")
    private int restaurantId;
    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "delivery")
    private boolean withDelivery;

//    @OneToMany(targetEntity = CartItem.class)
//    private List<CartItem> items = new ArrayList<>();
}