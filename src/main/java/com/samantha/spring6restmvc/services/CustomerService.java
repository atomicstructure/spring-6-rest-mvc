package com.samantha.spring6restmvc.services;


import com.samantha.spring6restmvc.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Samantha on 2023-10-01
 */
@Service
public interface CustomerService {

    List<Customer> listCustomers();

    Optional<Customer> getCustomerById(UUID uuid);

    Customer saveNewCustomer(Customer customer);

    void updateCustomerById(UUID customerId, Customer customer);

    void deleteById(UUID customerId);

    void patchCustomerById(UUID customerId, Customer customer);
}
