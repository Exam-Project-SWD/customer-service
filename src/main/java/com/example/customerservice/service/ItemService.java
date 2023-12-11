package com.example.customerservice.service;

import com.example.customerservice.model.entity.Item;
import com.example.customerservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public List<Item> getAllMenus() {
        return itemRepository.findAll();
    }
}