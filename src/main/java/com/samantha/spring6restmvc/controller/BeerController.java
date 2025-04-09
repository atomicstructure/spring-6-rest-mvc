package com.samantha.spring6restmvc.controller;

import com.samantha.spring6restmvc.model.Beer;
import com.samantha.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class BeerController {
    private final BeerService beerService;

    @RequestMapping("/api/v1/beer")
    public List<Beer> listBeers() {
        log.debug("Getting list of beers in Controller");
        return beerService.listBeers();
    }

    public Beer getBeerById(UUID id){

        log.debug("Getting beer by ID in Controller ");
        return beerService.getBeerById(id);
    }
}
