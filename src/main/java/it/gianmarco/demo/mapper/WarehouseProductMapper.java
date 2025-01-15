package it.gianmarco.demo.mapper;

import it.gianmarco.demo.entity.WarehouseProduct;
import it.gianmarco.demo.entity.dto.WarehouseProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WarehouseProductMapper {

    @Mapping(source = "dto.productId", target = "product.productId")
    @Mapping(source = "dto.quantity", target = "quantity")
    WarehouseProduct toEntity(WarehouseProductDto dto);

    @Mapping(source = "entity.product.productId", target = "productId")
    @Mapping(source = "entity.quantity", target = "quantity")
    WarehouseProductDto toDto(WarehouseProduct entity);

}
