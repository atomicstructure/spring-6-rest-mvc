package com.samantha.spring6restmvc.services;


import com.samantha.spring6restmvc.model.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Samantha on 2023-10-01
 */
@Service
public interface CustomerService {

    List<CustomerDTO> listCustomers();

    Optional<CustomerDTO> getCustomerById(UUID uuid);

    CustomerDTO saveNewCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer);

    boolean deleteById(UUID customerId);

    void patchCustomerById(UUID customerId, CustomerDTO customer);
}
