package com.example.customerservice.model.dto;

import java.io.Serializable;

/**
 * DTO for {@link com.example.customerservice.model.entity.Address}
 */
public record AddressDTO(int id, String street, String number, int postalCode) implements Serializable {
}