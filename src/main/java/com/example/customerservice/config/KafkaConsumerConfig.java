package com.example.customerservice.config;

import com.example.customerservice.model.entity.Item;
import com.example.customerservice.repository.ItemRepository;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private final ItemRepository itemRepository;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // Use StringDeserializer for the value type

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new StringDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> menuListener() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

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
