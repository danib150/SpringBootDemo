package it.gianmarco.demo.mapper;

import it.gianmarco.demo.entity.OrderProduct;
import it.gianmarco.demo.entity.dto.OrderProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderProductMapper {

    OrderProductDto toDto(OrderProduct orderProduct);

    OrderProduct toEntity(OrderProductDto orderProductDto);
}
