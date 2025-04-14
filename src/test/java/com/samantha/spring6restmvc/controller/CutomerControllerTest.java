package com.samantha.spring6restmvc.controller;


import com.samantha.spring6restmvc.model.Customer;
import com.samantha.spring6restmvc.services.CustomerService;
import com.samantha.spring6restmvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static  org.hamcrest.core.Is.is;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();

    @Test
    void testListCustomers() throws Exception{
        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMvc.perform(get("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getCustomerById() throws Exception {

        Customer testCustomer = customerServiceImpl.listCustomers().getFirst();

        given(customerService.getCustomerById(testCustomer.getId())).willReturn(testCustomer);

            mockMvc.perform(get("/api/v1/customer/" + testCustomer.getId())
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is(testCustomer.getId().toString())))
                    .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())));

    }
}
