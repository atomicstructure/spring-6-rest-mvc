package com.samantha.spring6restmvc.services;

import com.samantha.spring6restmvc.model.Beer;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface BeerService {
    Beer getBeerById(UUID id);
}
