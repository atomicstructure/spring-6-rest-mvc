package com.samantha.spring6restmvc.controller;

import com.samantha.spring6restmvc.model.Beer;
import com.samantha.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
    private final BeerService beerService;


    @PostMapping(consumes = "application/json")
    public ResponseEntity handlePost(@RequestBody Beer beer) {
        log.debug("Handling post in Controller");
        Beer savedBeer = beerService.saveNewBeer(beer);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers() {
        log.debug("Getting list of beers in Controller");
        return beerService.listBeers();
    }

    @RequestMapping(value = "{beerId}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("Getting beer by ID in Controller  123");
        return beerService.getBeerById(beerId);
    }

}
