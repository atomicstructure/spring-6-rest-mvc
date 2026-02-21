package com.samantha.spring6restmvc.controller;

import com.samantha.spring6restmvc.model.BeerDTO;
import com.samantha.spring6restmvc.model.BeerStyle;
import com.samantha.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_ID_PATH = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @PatchMapping(path = BEER_ID_PATH, consumes = "application/json")
    public ResponseEntity updateBeerPathById(@PathVariable("beerId") UUID beerId,@Validated @RequestBody BeerDTO beer) {
        beerService.patchBeerById(beerId, beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_ID_PATH)
    public  ResponseEntity deleteById(@PathVariable("beerId") UUID beerId) {
        if (!beerService.deleteById(beerId)) {;
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = BEER_ID_PATH, consumes = "application/json")
    public ResponseEntity updateByID(@PathVariable("beerId") UUID beerId, @Validated @RequestBody BeerDTO beer) {

        if (beerService.updateBeerById(beerId, beer).isEmpty()){
            throw new NotFoundException();
        };

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = BEER_PATH,consumes = "application/json")
    public ResponseEntity handlePost(@Validated @RequestBody BeerDTO beer) {

        BeerDTO savedBeer = beerService.saveNewBeer(beer);

       HttpHeaders headers = new HttpHeaders();
       if (savedBeer.getId() != null) {
           headers.add("Location", BEER_PATH + "/" + savedBeer.getId().toString());

       } else {
           UUID generatedId = UUID.randomUUID();
           headers.add("Location", BEER_PATH + "/" + generatedId.toString());
       }
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = BEER_PATH)
    public Page<BeerDTO> listBeers(@RequestParam(required = false) String beerName,
                                   @RequestParam(required = false) BeerStyle beerStyle,
                                   @RequestParam(required = false) Boolean showInventory,
                                   @RequestParam(required = false) Integer pageNumber,
                                   @RequestParam(required = false) Integer pageSize) {
        return beerService.listBeers(beerName, beerStyle, showInventory, pageNumber, pageSize);
    }


    @GetMapping(value = BEER_ID_PATH)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("Getting beer by ID in Controller  123");
        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

}
