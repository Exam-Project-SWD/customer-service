package com.example.customerservice.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartItemDTO {
    private int menuItemId;
    private int quantity;
}