package com.samantha.spring6restmvc.services;

import com.samantha.spring6restmvc.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService{

    private Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("Trophy")
                .version(1)
                .beerStyle(BeerStyle.LAGER)
                .upc("123456789012")
                .quantityOnHand(100)
                .price(new BigDecimal("10.99"))
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("Heineken")
                .version(1)
                .beerStyle(BeerStyle.IPA)
                .upc("123456789013")
                .quantityOnHand(50)
                .price(new BigDecimal("12.99"))
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("Legend Extra Stout")
                .version(1)
                .beerStyle(BeerStyle.STOUT)
                .upc("123456789014")
                .quantityOnHand(75)
                .price(new BigDecimal("11.99"))
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }

    @Override
    public List<Beer> listBeers(){
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Beer getBeerById(UUID id) {
                log.debug("Getting beer by ID: " + id.toString());
                return beerMap.get(id);

    }
}
