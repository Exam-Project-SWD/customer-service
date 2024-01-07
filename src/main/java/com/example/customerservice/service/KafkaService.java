package com.example.customerservice.service;

import com.example.customerservice.model.dto.AddressDTO;
import com.example.customerservice.model.dto.CartDTO;
import com.example.customerservice.model.dto.CustomerDTO;
import com.example.customerservice.model.entity.Cart;
import com.example.customerservice.model.enums.Topic;
import com.example.customerservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final CartRepository cartRepository;
    private final CustomerService customerService;
    private final AddressService addressService;

    public String sendCart(int customerId, int restaurantId) {
        Cart cart = cartRepository.findByCustomerIdAndRestaurantId(customerId, restaurantId);

        // Should be done by JPA association, but this is a quick solution.
        CustomerDTO customerDTO = customerService.getCustomerById(cart.getCustomerId());
        AddressDTO addressDTO = addressService.getAddressById(customerDTO.getAddressId());

        CartDTO cartDTO = new CartDTO(cart);
        cartDTO.setDeliveryAddress(addressDTO);

        kafkaTemplate.send(Topic.NEW_ORDER_PLACED.name(), cartDTO);
        return "NEW_ORDER_PLACED was published to Kafka";
    }
}