package com.example.customerservice.listener;

import com.example.customerservice.model.entity.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaListeners {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListeners.class);

    @KafkaListener(
            topics = "RESTAURANT_MENU",
            groupId = "restaurant-menu-id"
    )
    public void consumeRestaurantMenu(Set<Item> menu) {
        
        System.out.println("consumed: "+ menu);
        LOGGER.debug("&&& Message [{}] consumed", menu);
    }
}
