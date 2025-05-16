package com.samantha.spring6restmvc.repositories;

import com.samantha.spring6restmvc.entity.Beer;
import com.samantha.spring6restmvc.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;


    @Test
    void testSavedBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("Beer Name")
                        .beerStyle(BeerStyle.IPA)
                        .upc("123456789012")
                        .price(new BigDecimal("9.99"))
                .build());

        beerRepository.flush();
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}