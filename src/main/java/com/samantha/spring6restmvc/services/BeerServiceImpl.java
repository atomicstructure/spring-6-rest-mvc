package com.samantha.spring6restmvc.services;

import com.samantha.spring6restmvc.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class BeerServiceImpl implements BeerService{
    @Override
    public Beer getBeerById(UUID id) {
        return Beer.builder()
                .id(id)
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }
}
