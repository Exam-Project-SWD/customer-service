package com.example.customerservice.controller;

import com.example.customerservice.model.dto.CartDTO;
import com.example.customerservice.model.entity.Cart;
import com.example.customerservice.model.entity.Item;
import com.example.customerservice.service.CartService;
import com.example.customerservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/cart")
public class CartController {
    private final CartService cartService;
    private final ItemService itemService;

    @GetMapping("/menu")
    public ResponseEntity<List<Item>> getAllMenus() {
        return ResponseEntity.ok(itemService.getAllMenus());
    }


    @PostMapping("/add-items")
    public ResponseEntity<CartDTO> addItemToCart(@RequestParam("id") int id, @RequestBody Cart cart) {
        //TODO: Get id from header when customer is authorized
        return ResponseEntity.ok(cartService.addItemToCart(id, cart));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> showMyCart(@PathVariable("id") int id) {
        return ResponseEntity.ok(cartService.showMyCart(id));
    }

    @GetMapping("/checkout")
    public ResponseEntity<String> payCart(@RequestParam("customerId") int customerId, @RequestParam("restaurantId") int restaurantId) {
        cartService.payment(customerId, restaurantId);
        return ResponseEntity.ok("Payment was successful");
    }
}