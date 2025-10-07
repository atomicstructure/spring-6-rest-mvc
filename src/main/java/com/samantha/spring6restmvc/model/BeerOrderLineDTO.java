package com.samantha.spring6restmvc.model;

import com.samantha.spring6restmvc.entities.BeerOrder;
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

    public boolean isNew() {
        return this.id == null;
    }

    private BeerOrder beerOrder;
    private BeerDTO beer;

    private Integer orderQuantity;
    private Integer quantityAllocated;
}
