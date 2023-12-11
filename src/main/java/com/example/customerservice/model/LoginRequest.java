package com.example.customerservice.model;

public record LoginRequest(
        String email,
        String password
) {
}
