package com.samantha.spring6restmvc.bootstrap;

import com.samantha.spring6restmvc.entity.Beer;
import com.samantha.spring6restmvc.entity.Customer;
import com.samantha.spring6restmvc.model.BeerStyle;

import com.samantha.spring6restmvc.repositories.BeerRepository;
import com.samantha.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;


@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    
    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
    }

    private void loadBeerData() {
        if (beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()

                    .beerName("Trophy")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("123456789012")
                    .quantityOnHand(100)
                    .price(new BigDecimal("10.99"))
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()

                    .beerName("Heineken")
                    .beerStyle(BeerStyle.IPA)
                    .upc("123456789013")
                    .quantityOnHand(50)
                    .price(new BigDecimal("12.99"))
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()

                    .beerName("Legend Extra Stout")
                    .beerStyle(BeerStyle.STOUT)
                    .upc("123456789014")
                    .quantityOnHand(75)
                    .price(new BigDecimal("11.99"))
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            beerRepository.saveAll(Arrays.asList(beer1, beer2, beer3));
        }
    }

    private void loadCustomerData() {

        if (customerRepository.count() == 0){
            Customer customer1 = Customer.builder()

                    .customerName("Samantha")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()

                    .customerName("John")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();
            Customer customer3 = Customer.builder()

                    .customerName("Jane")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();


            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }
    }
}
