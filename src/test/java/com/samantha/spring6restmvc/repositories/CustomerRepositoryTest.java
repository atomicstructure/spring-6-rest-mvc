package com.samantha.spring6restmvc.repositories;

import com.samantha.spring6restmvc.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void savedCustomerTest() {
        Customer savedCustomer = customerRepository.save(Customer.builder()
                .customerName("Customer Name")
                .build());

        assertThat(savedCustomer).isNotNull();
        assertThat(Optional.of(savedCustomer.getId())).isNotNull();
    }
}