package com.samantha.spring6restmvc.repositories;

import com.samantha.spring6restmvc.bootstrap.BootStrapData;
import com.samantha.spring6restmvc.entities.Beer;
import com.samantha.spring6restmvc.model.BeerStyle;
import com.samantha.spring6restmvc.services.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
@Import({BootStrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetBeerListByName() {
        Page<Beer> beerPage = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", null);

        assertThat(beerPage.getContent().size());
    }

    @Test
    void testSavedBeerNameTooLong() {

        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("Beer Name Ogundare Oluwafemi Olusesi Beer Name Ogundare Oluwafemi Olusesi Beer Name Ogundare Oluwafemi Olusesi")
                    .beerStyle(BeerStyle.IPA)
                    .upc("123456789012")
                    .price(new BigDecimal("9.99"))
                    .build());

            beerRepository.flush();
        });
    }

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