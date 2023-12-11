package com.example.customerservice.service;

import com.example.customerservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartItemService {
    private final CartRepository cartRepository;


}
