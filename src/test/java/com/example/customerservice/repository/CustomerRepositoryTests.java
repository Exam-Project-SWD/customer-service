package com.example.customerservice.repository;

import com.example.customerservice.model.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    //Unit Test
    @Test
    public void registerCustomerTest() {
        Customer customer = new Customer();
        customer.setFirstName("mathias");
        customer.setLastName("jensen");
        customer.setEmail("mej@gmail.com");
        customer.setPhone("11345678");
        customer.setAddressId(5);
        customer.setRoles(List.of());
        customer.setPassword("password");

        Customer customerSaved = customerRepository.save(customer);

        Customer customerExist = entityManager.find(Customer.class, customerSaved.getId());

        assertThat(customer.getEmail()).isEqualTo(customerExist.getEmail());
    }
}