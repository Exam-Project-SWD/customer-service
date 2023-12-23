package com.example.customerservice.service;

import com.example.customerservice.model.CustomerRegistrationRequest;
import com.example.customerservice.model.LoginRequest;
import com.example.customerservice.model.dto.CustomerDTO;
import com.example.customerservice.model.entity.Address;
import com.example.customerservice.model.entity.Customer;
import com.example.customerservice.model.entity.Role;
import com.example.customerservice.repository.AddressRepository;
import com.example.customerservice.repository.CustomerRepository;
import com.example.customerservice.util.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    private final JwtToken jwtToken;


    public CustomerDTO registerCustomer(CustomerRegistrationRequest request) {

        Address address = Address.builder()
                .street(request.address().getStreet())
                .number(request.address().getNumber())
                .postalCode(request.address().getPostalCode())
                .build();

        Address newAddress = addressRepository.save(address);

        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phone(request.phone())
                .addressId(newAddress.getId())
                .roles(List.of(Role.builder()
                        .name("customer")
                        .build()))
                .password(encoder.encode(request.password()))
                .build();

        Customer newCustomer = customerRepository.save(customer);

        return CustomerDTO.builder()
                .firstName(newCustomer.getFirstName())
                .lastName(newCustomer.getLastName())
                .email(newCustomer.getEmail())
                .addressId(newCustomer.getAddressId())
                .phone(newCustomer.getPhone())
                .password(newCustomer.getPassword())
                .build();
    }


    public List<CustomerDTO> getCustomers() {
        List<CustomerDTO> customers = new ArrayList<>();
        for (Customer customer : customerRepository.findAll()) {
            customers.add(new CustomerDTO(customer));
        }
        return customers;
    }

    public CustomerDTO getCustomerById(int id) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        return new CustomerDTO(customer);
    }

    public CustomerDTO updateCustomer(int id, Customer customerRequest) {
        Customer customer = customerRepository.findById(id).orElseThrow();

        if (customerRequest.getFirstName() != null) {
            if (!customer.getFirstName().equals(customerRequest.getFirstName())) {
                customer.setFirstName(customerRequest.getFirstName());
            }
        }
        if (customerRequest.getLastName() != null) {
            if (!customer.getLastName().equals(customerRequest.getLastName())) {
                customer.setLastName(customerRequest.getLastName());
            }
        }
        //TODO: This should have some kind of check as it is a unique value
        if (customerRequest.getEmail() != null) {
            if (!customer.getEmail().equals(customerRequest.getEmail())) {
                customer.setEmail(customerRequest.getEmail());
            }
        }
        if (customerRequest.getAddressId() != 0) {
            if (customer.getAddressId() != customerRequest.getAddressId()) {
                customer.setAddressId(customerRequest.getAddressId());
            }
        }
        //TODO: This should have some kind of check as it is a unique value
        if (customerRequest.getPhone() != null) {
            if (!customer.getPhone().equals(customerRequest.getPhone())) {
                customer.setPhone(customer.getPhone());
            }
        }

        Customer newCustomer = customerRepository.save(customer);

        return new CustomerDTO(newCustomer);
    }

    public Map<String, Boolean> deleteCustomer(int id) {
        Customer customer = customerRepository.findById(id).orElseThrow();

        customerRepository.delete(customer);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);

        return response;
    }

    public String generateToken(LoginRequest request) throws Exception {
        Customer customer = customerRepository.findByEmail(request.email());

        if (customer != null && authenticate(request.password(), customer.getPassword())) {
            //return generated token
            return jwtToken.generateToken(request.email());
        } else {
            throw new Exception("email does not exist in the database");
        }
    }

    public boolean authenticate(String inputPassword, String storedPassword) {
        return encoder.matches(inputPassword, storedPassword);
    }
}