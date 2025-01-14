package it.gianmarco.demo.mapper;

import it.gianmarco.demo.controller.OrderDTO;
import it.gianmarco.demo.entity.Order;
import it.gianmarco.demo.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = Collectors.class)
public interface OrderMapper {

    // Conversione da Order a OrderDTO
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "products", target = "productIds", qualifiedByName = "mapProductIds")
    OrderDTO toDto(Order order);

    // Conversione da OrderDTO a Order
    @Mapping(source = "userId", target = "user", ignore = true)
    @Mapping(source = "productIds", target = "products", ignore = true)
    Order toEntity(OrderDTO orderDTO);

    @Named("mapProductIds")
    default List<Long> mapProductIds(List<Product> products) {
        return products.stream()
                .map(Product::getProductId)
                .collect(Collectors.toList());
    }




}
