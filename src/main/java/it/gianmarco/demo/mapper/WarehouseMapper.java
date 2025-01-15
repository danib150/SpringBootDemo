package it.gianmarco.demo.mapper;

import it.gianmarco.demo.entity.Warehouse;
import it.gianmarco.demo.entity.dto.WarehouseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = Collectors.class)
public interface WarehouseMapper {

    WarehouseMapper INSTANCE = Mappers.getMapper(WarehouseMapper.class);

    @Mapping(source = "name", target = "name")
    WarehouseDto toDto(Warehouse warehouse);

    @Mapping(source = "name", target = "name")
    Warehouse toEntity(WarehouseDto warehouseDto);
}
