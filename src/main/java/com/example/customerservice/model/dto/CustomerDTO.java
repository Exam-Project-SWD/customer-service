package com.example.customerservice.model.dto;

import com.example.customerservice.model.entity.Customer;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int addressId;

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.password = customer.getPassword();
        this.phone = customer.getPhone();
        this.addressId = customer.getAddressId();
    }
}