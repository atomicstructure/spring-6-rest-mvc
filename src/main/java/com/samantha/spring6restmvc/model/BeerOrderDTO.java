package com.samantha.spring6restmvc.model;

import com.samantha.spring6restmvc.entities.BeerOrderLine;
import com.samantha.spring6restmvc.entities.BeerOrderShipment;
import com.samantha.spring6restmvc.entities.Customer;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
public class BeerOrderDTO {

    private UUID id;
    private Long version;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;


    private String customerRef;

    private CustomerDTO customer;


    private Set<BeerOrderLineDTO> beerOrderLines;

    private BeerOrderShipmentDTO beerOrderShipment;
}
