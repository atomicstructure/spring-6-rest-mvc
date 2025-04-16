package com.samantha.spring6restmvc.controller;

import com.samantha.spring6restmvc.model.Beer;
import com.samantha.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer/";
    public static final String BEER_ID_PATH = "/api/v1/beer/{beerId}";

    private final BeerService beerService;

    @PatchMapping(path = BEER_ID_PATH, consumes = "application/json")
    public ResponseEntity updateBeerPathById(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
        beerService.patchBeerById(beerId, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_ID_PATH)
    public  ResponseEntity deleteById(@PathVariable("beerId") UUID beerId) {
        beerService.deleteById(beerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = BEER_ID_PATH, consumes = "application/json")
    public ResponseEntity updateByID(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
        beerService.updateBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = BEER_PATH,consumes = "application/json")
    public ResponseEntity handlePost(@RequestBody Beer beer) {
        log.debug("Handling post in Controller");
        Beer savedBeer = beerService.saveNewBeer(beer);

       HttpHeaders headers = new HttpHeaders();
       headers.add("Location", BEER_PATH + "/" + savedBeer.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = BEER_PATH)
    public List<Beer> listBeers() {
        log.debug("Getting list of beers in Controller");
        return beerService.listBeers();
    }

    @GetMapping(value = BEER_ID_PATH)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("Getting beer by ID in Controller  123");
        return beerService.getBeerById(beerId);
    }

}
