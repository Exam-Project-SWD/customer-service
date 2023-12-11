package com.example.customerservice.integration;

import com.example.customerservice.model.CustomerRegistrationRequest;
import com.example.customerservice.model.LoginRequest;
import com.example.customerservice.model.dto.CustomerDTO;
import com.example.customerservice.model.entity.Customer;
import com.example.customerservice.model.entity.Role;
import com.example.customerservice.service.CustomerService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerService customerService;

    @Test
    public void registerCustomer_ValidRequest_Success() {
        // Given
        String baseUrl = "http://localhost:" + port + "/customer/registration";
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "mathias",
                "jensen",
                "mj@gmail.com",
                1,
                "12345678",
                "password"
        );
        HttpEntity<CustomerRegistrationRequest> requestEntity = new HttpEntity<>(request);

        // When
        ResponseEntity<CustomerDTO> response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, CustomerDTO.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void login_ValidRequest_Success() {
        // Given
        String baseUrl = "http://localhost:" + port + "/customer/login";
        LoginRequest request = new LoginRequest(
                "mj@gmail.com",
                "password"
        );
        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(request);

        // When
        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, String.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getCustomers_NoInput_Success() {
        // Given
        String baseUrl = "http://localhost:" + port + "/customer/all";

        // When
        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl, List.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getCustomerById_ValidId_Success() {
        // Given
        int customerId = 1;
        String baseUrl = "http://localhost:" + port + "/customer/" + customerId;

        // When
        ResponseEntity<CustomerDTO> response = restTemplate.getForEntity(baseUrl, CustomerDTO.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void updateCustomer_ValidIdAndCustomer_Success() {
        // Given
        int customerId = 1;
        String baseUrl = "http://localhost:" + port + "/customer/" + customerId;
        Customer customer = Customer.builder()
                .firstName("mathias")
                .lastName("jensen")
                .email("mj@gmail.com")
                .phone("12345678")
                .addressId(1)
                .roles(List.of(Role.builder()
                        .name("customer")
                        .build()))
                .password("password")
                .build();
        HttpEntity<Customer> requestEntity = new HttpEntity<>(customer);

        // When
        ResponseEntity<CustomerDTO> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity, CustomerDTO.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void deleteCustomer_ValidId_Success() {
        // Given
        int customerId = 1;
        String baseUrl = "http://localhost:" + port + "/customer/" + customerId;

        // When
        ResponseEntity<Map> response = restTemplate.exchange(baseUrl, HttpMethod.DELETE, null, Map.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}