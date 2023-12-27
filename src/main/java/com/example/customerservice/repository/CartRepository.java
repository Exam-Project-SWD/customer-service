package com.example.customerservice.repository;

import com.example.customerservice.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByCustomerIdAndRestaurantId(int customerId, int restaurantId);
}