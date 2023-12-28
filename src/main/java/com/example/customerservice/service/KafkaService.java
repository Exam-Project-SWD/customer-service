package com.example.customerservice.service;

import com.example.customerservice.model.entity.Cart;
import com.example.customerservice.model.enums.Topic;
import com.example.customerservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaService {
    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private final CartRepository cartRepository;

    public String sendCart(int customerId, int restaurantId) {
        Cart cart = cartRepository.findByCustomerIdAndRestaurantId(customerId, restaurantId);

        kafkaTemplate.send(Topic.NEW_ORDER_PLACED.toString(), cart);
        return "NEW_ORDER_PLACED was published to Kafka";
    }
}
