package com.example.customerservice.integration;

import com.example.customerservice.controller.CustomerController;
import com.example.customerservice.model.LoginRequest;
import com.example.customerservice.model.dto.CustomerDTO;
import com.example.customerservice.model.entity.Customer;
import com.example.customerservice.model.entity.Role;
import com.example.customerservice.service.CustomerService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EmbeddedKafka(partitions = 1, bootstrapServersProperty = "spring.embedded.kafka.broker")
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerService customerService;

    @Test
    public void registerCustomer_ValidRequest_Success() throws Exception {
        // Given
        String requestBody = "{"
                + "\"firstName\":\"John\","
                + "\"lastName\":\"Doe\","
                + "\"email\":\"john.doe@example.com\","
                + "\"addressId\":3,"
                + "\"phone\":\"12345678\","
                + "\"password\":\"password\""+"}";

        // When
        mvc.perform(post("/customer/registration").contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(status().isOk());
    }

//    @Test
//    public void login_ValidRequest_Success() {
//        // Given
//        String baseUrl = "http://localhost:" + port + "/customer/login";
//        LoginRequest request = new LoginRequest(
//                "mj@gmail.com",
//                "password"
//        );
//        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(request);
//
//        // When
//        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, requestEntity, String.class);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//    }
//
//    @Test
//    public void getCustomers_NoInput_Success() {
//        // Given
//        String baseUrl = "http://localhost:" + port + "/customer/all";
//
//        // When
//        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl, List.class);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//    }
//
//    @Test
//    public void getCustomerById_ValidId_Success() {
//        // Given
//        int customerId = 1;
//        String baseUrl = "http://localhost:" + port + "/customer/" + customerId;
//
//        // When
//        ResponseEntity<CustomerDTO> response = restTemplate.getForEntity(baseUrl, CustomerDTO.class);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//    }
//
//    @Test
//    public void updateCustomer_ValidIdAndCustomer_Success() {
//        // Given
//        int customerId = 1;
//        String baseUrl = "http://localhost:" + port + "/customer/" + customerId;
//        Customer customer = Customer.builder()
//                .firstName("mathias")
//                .lastName("jensen")
//                .email("mj@gmail.com")
//                .phone("12345678")
//                .addressId(1)
//                .roles(List.of(Role.builder()
//                        .name("customer")
//                        .build()))
//                .password("password")
//                .build();
//        HttpEntity<Customer> requestEntity = new HttpEntity<>(customer);
//
//        // When
//        ResponseEntity<CustomerDTO> response = restTemplate.exchange(baseUrl, HttpMethod.PUT, requestEntity, CustomerDTO.class);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//    }
//
//    @Test
//    public void deleteCustomer_ValidId_Success() {
//        // Given
//        int customerId = 1;
//        String baseUrl = "http://localhost:" + port + "/customer/" + customerId;
//
//        // When
//        ResponseEntity<Map> response = restTemplate.exchange(baseUrl, HttpMethod.DELETE, null, Map.class);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//    }
}