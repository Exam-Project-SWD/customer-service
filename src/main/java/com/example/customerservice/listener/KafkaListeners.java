package com.example.customerservice.listener;

import com.example.customerservice.model.entity.Item;
import com.example.customerservice.repository.ItemRepository;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class KafkaListeners {
    @Autowired
    private final ItemRepository itemRepository;

    @KafkaListener(topics = "RESTAURANT_MENU", groupId = "restaurant-menu-id")
    public void listen(String message) {
        try {
            // Assuming the message is a JSON string representing a list of sets of items
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType stringType = objectMapper.getTypeFactory().constructType(String.class);
            JavaType itemType = objectMapper.getTypeFactory().constructType(Item.class);
            JavaType setType = objectMapper.getTypeFactory().constructCollectionType(Set.class, itemType);
            JavaType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, stringType, setType);


            Map<String, Set<Item>> menuMap = objectMapper.readValue(message, mapType);

            // Now you have a List<Set<Item>> that you can process
            menuMap.forEach((key, value) -> {
                        for (Item i: value) {
                            Item item = new Item();
                            item.setId(i.getId());
                            item.setName(i.getName());
                            item.setDescription(i.getDescription());
                            item.setPrice(i.getPrice());
                            item.setRestaurantId(Integer.parseInt(key));

                            //TODO: check if item exists and if so update
                            itemRepository.save(item);
                        }
                    }
            );
            //System.out.println("Received: " + itemsList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
