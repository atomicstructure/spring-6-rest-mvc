package com.samantha.spring6restmvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface Customer extends JpaRepository<Customer, UUID> {
}
