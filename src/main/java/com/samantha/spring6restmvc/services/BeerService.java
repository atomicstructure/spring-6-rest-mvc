package com.samantha.spring6restmvc.services;

import com.samantha.spring6restmvc.model.Beer;

import java.util.UUID;

public interface BeerService {
    Beer getBeerById(UUID id);
}
