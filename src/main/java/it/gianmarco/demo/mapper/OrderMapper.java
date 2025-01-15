package it.gianmarco.demo.mapper;

import it.gianmarco.demo.entity.dto.OrderDto;
import it.gianmarco.demo.entity.Order;
import it.gianmarco.demo.entity.OrderProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderProducts", target = "productIds", qualifiedByName = "mapProductIds")
    OrderDto toDto(Order order);

    @Mapping(source = "userId", target = "user", ignore = true)
    @Mapping(source = "productIds", target = "orderProducts", ignore = true)
    Order toEntity(OrderDto orderDTO);

    @Named("mapProductIds")
    default List<Long> mapProductIds(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(orderProduct -> orderProduct.getProduct().getProductId())
                .collect(Collectors.toList());
    }
}
