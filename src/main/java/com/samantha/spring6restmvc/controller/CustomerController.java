package com.samantha.spring6restmvc.controller;

import com.samantha.spring6restmvc.model.Customer;
import com.samantha.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/customer")
@RestController
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    public void updateCustomerById(UUID customerId, Customer customer) {
        customerService.updateCustomerById(customerId, customer);
    }

    @PatchMapping("{customerId}")
    public ResponseEntity updateCustomerPathById(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer) {
        customerService.patchCustomerById(customerId, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping("{customerId}")
    public  ResponseEntity deleteById(@PathVariable("customerId") UUID customerId) {
        customerService.deleteById(customerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PutMapping(value = "{customerId}", consumes = "application/json")
    public ResponseEntity updateById(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer) {
        customerService.updateCustomerById(customerId, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity handlePost(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listCustomers() {
        return customerService.listCustomers();
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {

        return customerService.getCustomerById(customerId);
    }

}
