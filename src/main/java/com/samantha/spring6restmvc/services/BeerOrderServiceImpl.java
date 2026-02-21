package com.samantha.spring6restmvc.services;

import com.samantha.spring6restmvc.mappers.BeerOrderMapper;
import com.samantha.spring6restmvc.model.BeerOrderDTO;
import com.samantha.spring6restmvc.repositories.BeerOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BeerOrderServiceImpl implements BeerOrderService {
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;

    @Override
    public Optional<BeerOrderDTO> getById(UUID beerOrderId) {
        return Optional.ofNullable(beerOrderMapper.beerOrderToBeerOrderDto(beerOrderRepository.findById(beerOrderId)
                .orElse(null)));
    }

    @Override
    public Page<BeerOrderDTO> listOrders(Integer pageNumber, Integer pageSize) {
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 0;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
    }
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return beerOrderRepository.findAll(pageRequest)
                .map(beerOrderMapper::beerOrderToBeerOrderDto);
    }
}
