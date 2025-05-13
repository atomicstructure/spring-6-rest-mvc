package com.samantha.spring6restmvc.mappers;


import com.samantha.spring6restmvc.entity.Customer;
import com.samantha.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);
}
