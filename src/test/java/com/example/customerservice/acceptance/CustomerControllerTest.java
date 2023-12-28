package com.example.customerservice.acceptance;

import com.example.customerservice.controller.CustomerController;
import com.example.customerservice.model.CustomerRegistrationRequest;
import com.example.customerservice.model.LoginRequest;
import com.example.customerservice.model.dto.CustomerDTO;
import com.example.customerservice.model.entity.Customer;
import com.example.customerservice.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

//    @Test
//    void registerCustomer_ValidRequest_Success() {
//        // Mocking
//        when(customerService.registerCustomer(any(CustomerRegistrationRequest.class)))
//                .thenReturn(CustomerDTO.builder()
//                        .firstName("mathias")
//                        .lastName("jensen")
//                        .email("mj@gmail.com")
//                        .phone("12345678")
//                        .addressId(1)
//                        .password("password")
//                        .build());
//        // Test
//        ResponseEntity<CustomerDTO> response = customerController.registerCustomer(new CustomerRegistrationRequest(
//                "mathias",
//                "jensen",
//                "mj@gmail.com",
//                1,
//                "12345678",
//                "password"
//        ));
//
//        // Assertions
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//    }

    @Test
    void login_ValidRequest_Success() throws Exception {
        // Mocking
        when(customerService.generateToken(any(LoginRequest.class)))
                .thenReturn("mockedToken");

        // Test
        ResponseEntity<String> response = customerController.login(new LoginRequest(
                "mj@gmail.com",
                "password"
        ));

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getCustomers_NoInput_Success() {
        // Mocking
        when(customerService.getCustomers())
                .thenReturn(Arrays.asList(new CustomerDTO(
                        "mathias",
                        "jensen",
                        "mj@gmail.com",
                        "password",
                        "12345678",
                        1)));

        // Test
        ResponseEntity<List<CustomerDTO>> response = customerController.getCustomers();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getCustomerById_ValidId_Success() {
        // Mocking
        when(customerService.getCustomerById(anyInt()))
                .thenReturn(new CustomerDTO(
                        "mathias",
                        "jensen",
                        "mj@gmail.com",
                        "password",
                        "12345678",
                        1));

        // Test
        ResponseEntity<CustomerDTO> response = customerController.getCustomerById(1);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void updateCustomer_ValidIdAndCustomer_Success() {
        // Mocking
        when(customerService.updateCustomer(anyInt(), any(Customer.class)))
                .thenReturn(new CustomerDTO(
                        "mathias",
                        "jensen",
                        "mj@gmail.com",
                        "password",
                        "12345678",
                        1
                ));

        // Test
        ResponseEntity<CustomerDTO> response = customerController.updateCustomer(1, new Customer());

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void deleteCustomer_ValidId_Success() {
        // Mocking
        when(customerService.deleteCustomer(anyInt()))
                .thenReturn(Map.of("success", true));

        // Test
        ResponseEntity<Map<String, Boolean>> response = customerController.deleteCustomer(1);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().get("success"));
    }
}