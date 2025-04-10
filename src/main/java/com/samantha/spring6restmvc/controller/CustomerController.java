package com.samantha.spring6restmvc.controller;

import com.samantha.spring6restmvc.model.Customer;
import com.samantha.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/customer")
@RestController
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listCustomers() {
        return customerService.listCustomers();
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {

        return customerService.getCustomerById(customerId);
    }

}
