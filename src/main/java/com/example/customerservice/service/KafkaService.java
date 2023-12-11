package com.example.customerservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class KafkaService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
}
