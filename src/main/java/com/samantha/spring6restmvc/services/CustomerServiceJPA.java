package com.samantha.spring6restmvc.services;

import com.samantha.spring6restmvc.mappers.CustomerMapper;
import com.samantha.spring6restmvc.model.CustomerDTO;
import com.samantha.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService{
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;


    @Cacheable(cacheNames = "customerListCache")
    @Override
    public List<CustomerDTO> listCustomers() {
        log.info("ListCustomers in Service");
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }


    @Cacheable(cacheNames = "customerCache", key = "#uuid", condition = "#showInventory == false")
    @Override
    public Optional<CustomerDTO> getCustomerById(UUID uuid) {
        log.info("GetCustomerById in Service");
        return Optional.ofNullable(customerMapper
                .customerToCustomerDto(customerRepository.findById(uuid).orElse(null)));
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        return customerMapper.customerToCustomerDto(customerRepository.save(customerMapper.customerDtoToCustomer(customer)));
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(customerId).ifPresentOrElse(customerEntity -> {
            customerEntity.setCustomerName(customer.getCustomerName());
            customerEntity.setVersion(customer.getVersion());
            atomicReference.set(Optional.of(customerMapper
                    .customerToCustomerDto(customerRepository.save(customerEntity))));

        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return  atomicReference.get();
    }

    @Override
    public boolean deleteById(UUID customerId) {
        if (customerRepository.existsById(customerId)) {;
            customerRepository.deleteById(customerId);
            return true;
        }

        return false;
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {

    }
}
