package com.samantha.spring6restmvc.services;


import com.samantha.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

/**
 * Created by Samantha on 2023-10-01
 */
public interface CustomerService {

    List<Customer> listCustomers();

    Customer getCustomerById(UUID uuid);

    Customer saveNewCustomer(Customer customer);

    void updateCustomerById(UUID customerId, Customer customer);

    void deleteById(UUID customerId);

    void patchCustomerById(UUID customerId, Customer customer);
}
