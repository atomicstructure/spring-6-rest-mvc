package com.samantha.spring6restmvc.bootstrap;

import com.samantha.spring6restmvc.repositories.BeerRepository;
import com.samantha.spring6restmvc.repositories.CustomerRepository;
import com.samantha.spring6restmvc.services.BeerCsvService;
import com.samantha.spring6restmvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@Import(BeerCsvServiceImpl.class)
class BootStrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    BootStrapData bootStrapData;

    @Autowired
    private BeerCsvService beerCsvService;

    @BeforeEach
    void setUp() {
        bootStrapData = new BootStrapData(beerRepository, customerRepository, beerCsvService);
    }

    @Test
    void Testrun() throws Exception {
        bootStrapData.run(null);

        assertThat(beerRepository.count()).isEqualTo(2413);

        assertThat(customerRepository.count()).isEqualTo(3);
    }
}