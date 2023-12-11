package com.example.customerservice.controller;

import com.example.customerservice.model.dto.CartDTO;
import com.example.customerservice.model.entity.Cart;
import com.example.customerservice.model.entity.Item;
import com.example.customerservice.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/cart")
public class CartController {
    private final CartService cartService;



    @PostMapping("/add-items")
    public ResponseEntity<CartDTO> addItemToCart(@RequestParam("id") int id, @RequestBody Cart cart) {
        //TODO: Get id from header when customer is authorized
        return ResponseEntity.ok(cartService.addItemToCart(id, cart));
    }
}
