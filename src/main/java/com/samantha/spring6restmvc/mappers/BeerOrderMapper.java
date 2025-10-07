package com.samantha.spring6restmvc.mappers;

import com.samantha.spring6restmvc.entities.BeerOrder;
import com.samantha.spring6restmvc.entities.BeerOrderLine;
import com.samantha.spring6restmvc.entities.BeerOrderShipment;
import com.samantha.spring6restmvc.model.BeerOrderDTO;
import com.samantha.spring6restmvc.model.BeerOrderLineDTO;
import com.samantha.spring6restmvc.model.BeerOrderShipmentDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerOrderMapper {

    BeerOrderShipment beerOrderShiptmentDtoToBeerOrderShipment(BeerOrderShipmentDTO beerOrderShipmentDTO);

    BeerOrderShipmentDTO beerOrderShipmentToBeerOrderShipmentDTO(BeerOrderShipment beerOrderShipment);

    BeerOrderLine beerOrderLineDtoToBeerOrderLine(BeerOrderLineDTO beerOrderLineDTO);

    BeerOrderLineDTO beerOrderLineToBeerOrderLineDto(BeerOrderLine beerOrderLine);

    BeerOrder beerOrderDtoToBeerOrder(BeerOrderDTO beerOrderDTO);

    BeerOrderDTO beerOrderToBeerOrderDto(BeerOrder beerOrder);
}
