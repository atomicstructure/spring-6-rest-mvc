package com.samantha.spring6restmvc.controller;


import com.samantha.spring6restmvc.entities.Customer;
import com.samantha.spring6restmvc.mappers.CustomerMapper;
import com.samantha.spring6restmvc.model.CustomerDTO;
import com.samantha.spring6restmvc.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;


    @Test
    void testDeleteCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteById(UUID.randomUUID());
        });
    }

    @Test
    void testDeleteCustomerById() {
        Customer customer = customerRepository.findAll().getFirst();
        ResponseEntity responseEntity = customerController.deleteById(customer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Optional<Customer> deletedCustomer = customerRepository.findById(customer.getId());
        assertThat(deletedCustomer).isEmpty();
    }

    @Test
    void testUpdateCustomerNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.updateById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Test
    void testUpdateCustomer() {
        Customer customer = customerRepository.findAll().getFirst();
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);


        final String customerName = "Updated";

        customerDTO.setCustomerName(customerName);
        ResponseEntity responseEntity = customerController.updateById(customer.getId(), customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getCustomerName()).isEqualTo(customerName);
    }

    @Rollback
    @Transactional
    @Test
    void testNewSaveCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("New Customer")
                .build();

        ResponseEntity responseEntity = customerController.handlePost(customerDTO);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Customer customer = customerRepository.findById(savedUUID).get();
        assertThat(customer).isNotNull();
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().getFirst();
        Optional<CustomerDTO> dto = customerController.getCustomerById(customer.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void testCustomerIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Test
    void testListCustomers() {
        List<CustomerDTO> dtos = customerController.listCustomers();

        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> dtos = customerController.listCustomers();

        assertThat(dtos.size()).isEqualTo(0);
    }
}