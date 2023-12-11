package com.example.customerservice.listener;

import com.example.customerservice.model.entity.Item;
import com.example.customerservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaListeners {
    private final ItemRepository itemRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListeners.class);

    @KafkaListener(
            topics = "RESTAURANT_MENU",
            groupId = "restaurant-menu-id"
    )
    public void consumeRestaurantMenu(List<Set<Item>> menus) {

        //gem consumed data
        //menus.forEach(System.out::println);
        for (Set<Item> i: menus
             ) {
            System.out.println(i);
        }


        System.out.println("consumed: "+ menus);
        //LOGGER.debug("&&& Message [{}] consumed", menus);
    }
}
