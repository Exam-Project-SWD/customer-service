package com.example.customerservice.unit;

import com.example.customerservice.model.dto.CustomerDTO;
import com.example.customerservice.model.entity.Customer;
import com.example.customerservice.model.entity.Role;
import com.example.customerservice.repository.CustomerRepository;
import com.example.customerservice.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testGetCustomersReturnsEmptyListWhenNoCustomersFound() {
        // Arrange
        when(customerRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<CustomerDTO> result = customerService.getCustomers();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetCustomersMapsCustomersToDTOs() {
        // Arrange
        Customer customer1 = Customer.builder()
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
        Customer customer2 = Customer.builder()
                .firstName("tom")
                .lastName("madsen")
                .email("tm@gmail.com")
                .phone("87654321")
                .addressId(2)
                .roles(List.of(Role.builder()
                        .name("customer")
                        .build()))
                .password("password")
                .build();
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        // Act
        List<CustomerDTO> result = customerService.getCustomers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetCustomerByIdReturnsCustomerDTO() {
        // Arrange
        int customerId = 1;
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
        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.of(customer));

        // Act
        CustomerDTO result = customerService.getCustomerById(customerId);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testGetCustomerByIdThrowsExceptionForInvalidId() {
        // Arrange
        int invalidCustomerId = 999;
        when(customerRepository.findById(invalidCustomerId)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> customerService.getCustomerById(invalidCustomerId));
    }

    @Test
    void testUpdateCustomerSuccessfully() {
        // Arrange
        int customerId = 1;
        Customer existingCustomer = Customer.builder()
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
        Customer updatedCustomerRequest = Customer.builder()
                .firstName("Mathias")
                .lastName("Jensen")
                .email("MJ@gmail.com")
                .phone("12345678")
                .addressId(1)
                .roles(List.of(Role.builder()
                        .name("customer")
                        .build()))
                .password("password")
                .build();;
        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        CustomerDTO result = customerService.updateCustomer(customerId, updatedCustomerRequest);

        // Assert
        assertNotNull(result);
        assertEquals(existingCustomer.getFirstName(), updatedCustomerRequest.getFirstName());
        assertEquals(existingCustomer.getLastName(), updatedCustomerRequest.getLastName());
    }

    @Test
    void testUpdateCustomerThrowsExceptionForNonexistentCustomer() {
        // Arrange
        int nonexistentCustomerId = 999;
        Customer updatedCustomerRequest = Customer.builder()
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
        when(customerRepository.findById(nonexistentCustomerId)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> customerService.updateCustomer(nonexistentCustomerId, updatedCustomerRequest));
    }
    @Test
    void testDeleteCustomerSuccessfully() {
        // Arrange
        int customerId = 1;
        Customer existingCustomer = Customer.builder()
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
        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.of(existingCustomer));

        // Act
        Map<String, Boolean> result = customerService.deleteCustomer(customerId);

        // Assert
        assertNotNull(result);
        assertTrue(result.get("Deleted"));
        verify(customerRepository, times(1)).delete(existingCustomer);
    }

    @Test
    void testDeleteCustomerThrowsExceptionForNonexistentCustomer() {
        // Arrange
        int nonexistentCustomerId = 999;
        when(customerRepository.findById(nonexistentCustomerId)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> customerService.deleteCustomer(nonexistentCustomerId));
        verify(customerRepository, never()).delete(any());
    }
}
