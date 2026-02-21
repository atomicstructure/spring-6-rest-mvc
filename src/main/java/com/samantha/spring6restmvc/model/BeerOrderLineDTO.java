package com.samantha.spring6restmvc.model;

import com.samantha.spring6restmvc.entities.BeerOrder;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Data
public class BeerOrderLineDTO {
    private UUID id;

    private Long version;

    private Timestamp createdDate;

    private Timestamp lastModifiedDate;
    private BeerOrder beerOrder;
    private BeerDTO beer;

    @Min(value = 1, message = "Quantity on hand must be greater than 0")
    private Integer orderQuantity;
    private Integer quantityAllocated;
    private BeerOrderLineStatus orderLineStatus;
}
