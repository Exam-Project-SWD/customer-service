package com.example.customerservice.model;

import com.example.customerservice.model.entity.Address;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email,
        Address address,
        String phone,
        String password
) {
}
