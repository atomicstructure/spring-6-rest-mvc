package com.samantha.spring6restmvc.services;

import com.samantha.spring6restmvc.entities.Beer;
import com.samantha.spring6restmvc.mappers.BeerMapper;
import com.samantha.spring6restmvc.model.BeerDTO;
import com.samantha.spring6restmvc.model.BeerStyle;
import com.samantha.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService{
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;


    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;

    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = builPageRequest(pageNumber, pageSize);
        Page<Beer> beerPage;

        if (StringUtils.hasText(beerName) && beerStyle == null) {

            beerPage = listBeerByName(beerName, pageRequest);
        }
        else if (!StringUtils.hasText(beerName) && beerStyle != null){

            beerPage = listBeerByStyle(beerStyle, pageRequest);
        }
        else if (StringUtils.hasText(beerName) && beerStyle != null){
            beerPage = listBeerByNameAndStyle(beerName, beerStyle, pageRequest);
        }
        else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (showInventory == null || !showInventory) {
            beerPage.forEach(beer -> beer.setQuantityOnHand(null));
        }

        return beerPage.map(beerMapper::beerToBeerDto);
//        return beerPage.stream()
//                .map(beerMapper::beerToBeerDto)
//                .collect(Collectors.toList());

    }

    public PageRequest builPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber >= 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }
        if (pageSize == null){
            queryPageSize = DEFAULT_PAGE_SIZE;
        }else {
            if (pageSize > 1000) {
                queryPageSize = 1000;
            } else {
                queryPageSize = pageSize;
            }
        }
        return PageRequest.of(queryPageNumber, queryPageSize);
    }

    private Page<Beer> listBeerByNameAndStyle(String beerName, BeerStyle beerStyle, PageRequest pageRequest) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%" + beerName + "%" , beerStyle, null);
    }

    public Page<Beer> listBeerByStyle(BeerStyle beerStyle, PageRequest pageRequest) {

        return beerRepository.findAllByBeerStyle(beerStyle, null);

    }

    public Page<Beer> listBeerByName(String beerName, PageRequest pageRequest) {

        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" +beerName + "%", null);

    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(beerEntity -> {
            beerEntity.setBeerName(beer.getBeerName());
            beerEntity.setBeerStyle(beer.getBeerStyle());
            beerEntity.setUpc(beer.getUpc());
            beerEntity.setPrice(beer.getPrice());
            atomicReference.set(Optional.of(beerMapper
                    .beerToBeerDto(beerRepository.save(beerEntity))));

        }, () -> {;
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public boolean deleteById(UUID beerId) {
        if (beerRepository.existsById(beerId)) {;
            beerRepository.deleteById(beerId);
            return true;
        }
        return false;
    }

    @Override
    public void patchBeerById(UUID beerId, BeerDTO beer) {

    }
}
