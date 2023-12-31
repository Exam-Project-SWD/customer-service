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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "customer_id")
    private int customerId;
    @Column(name = "restaurant_id")
    private int restaurantId;
    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "delivery")
    private boolean withDelivery = true;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "cart_items",
            joinColumns = {@JoinColumn(name = "cart_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "cart_item_id", referencedColumnName = "id")})
    private List<CartItem> items = new ArrayList<>();
}