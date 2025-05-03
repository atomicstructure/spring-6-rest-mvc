package com.samantha.spring6restmvc.services;

import com.samantha.spring6restmvc.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public Optional<Beer> getBeerById(UUID id) {
        log.debug("Getting beer by ID: " + id.toString());
        Beer beer = beerMap.get(id);

        return Optional.ofNullable(beer);
    }

    @Override
    public Beer saveNewBeer(Beer beer) {

        Beer savedBeer = Beer.builder()
                .id(UUID.randomUUID())
                .beerName(beer.getBeerName())
                .version(1)
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .quantityOnHand(beer.getQuantityOnHand())
                .price(beer.getPrice())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
        beerMap.put(savedBeer.getId(), savedBeer);
        return savedBeer;
    }

    @Override
    public void updateBeerById(UUID beerId, Beer beer) {
        Beer existingBeer = beerMap.get(beerId);

        if (existingBeer != null) {
            existingBeer.setBeerName(beer.getBeerName());
            existingBeer.setBeerStyle(beer.getBeerStyle());
            existingBeer.setUpc(beer.getUpc());
            existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
            existingBeer.setPrice(beer.getPrice());
            existingBeer.setVersion(beer.getVersion());
            existingBeer.setUpdatedDate(LocalDateTime.now());
        }
    }

    @Override
    public void deleteById(UUID beerId) {


        beerMap.remove(beerId);
    }



    @Override
    public void patchBeerById(UUID beerId, Beer beer) {
        Beer existingBeer = beerMap.get(beerId);



        boolean updated = false;

        if (StringUtils.hasText(beer.getBeerName())){
            existingBeer.setBeerName(beer.getBeerName());
            updated = true;
        }
        if (beer.getBeerStyle() != null){
            existingBeer.setBeerStyle(beer.getBeerStyle());
            updated = true;
        }
        if (StringUtils.hasText(beer.getUpc())) {
            existingBeer.setUpc(beer.getUpc());
            updated = true;
        }
        if (beer.getPrice() != null) {
            existingBeer.setPrice(beer.getPrice());
            updated = true;
        }
        if (beer.getQuantityOnHand() != null) {
            existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
            updated = true;
        }

        if (updated) {
            existingBeer.setUpdatedDate(LocalDateTime.now());
        }
    }
}
