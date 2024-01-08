package com.example.customerservice.controller;

import com.example.customerservice.model.CustomerRegistrationRequest;
import com.example.customerservice.model.LoginRequest;
import com.example.customerservice.model.dto.CustomerDTO;
import com.example.customerservice.model.entity.Customer;
import com.example.customerservice.service.CustomerService;
import com.example.customerservice.service.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/customer")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(Authentication.class);

    private final CustomerService customerService;
    private final KafkaService kafkaService;

    @PostMapping("/registration")
    public ResponseEntity<CustomerDTO> registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        CustomerDTO customerDTO = customerService.registerCustomer(request);
        kafkaService.sendChangedCustomer(customerDTO);
        return ResponseEntity.ok(customerDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) throws Exception {
        LOGGER.debug("Request was made with email: " + request.email());
        String token = customerService.generateToken(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable(value = "id") int id, @RequestBody Customer customer) {
        CustomerDTO customerDTO = customerService.updateCustomer(id, customer);
        kafkaService.sendChangedCustomer(customerDTO);
        return ResponseEntity.ok(customerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCustomer(@PathVariable(value = "id") int id) {
        Map<String, Boolean> response = customerService.deleteCustomer(id);
        kafkaService.sendDeletedCustomer(id);
        return ResponseEntity.ok(response);
    }
}