package com.example.customerservice.config;

import com.example.customerservice.model.enums.Topic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic newOrderPlacedTopic() {
        return TopicBuilder.name(Topic.NEW_ORDER_PLACED.toString()).build();
    }
}