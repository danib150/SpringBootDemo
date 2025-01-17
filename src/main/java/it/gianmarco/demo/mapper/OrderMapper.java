package it.gianmarco.demo.mapper;

import it.gianmarco.demo.entity.dto.OrderDto;
import it.gianmarco.demo.entity.Order;
import it.gianmarco.demo.entity.OrderProduct;
import it.gianmarco.demo.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderProducts", target = "productQuantities", qualifiedByName = "mapOrderProductsToProductQuantities")
    OrderDto toDto(Order order);

    @Mapping(source = "userId", target = "user", ignore = true)
    @Mapping(source = "productQuantities", target = "orderProducts", qualifiedByName = "mapProductQuantitiesToOrderProducts")
    Order toEntity(OrderDto orderDTO);

    @Named("mapOrderProductsToProductQuantities")
    default Map<Long, Integer> mapOrderProductsToProductQuantities(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .collect(Collectors.toMap(
                        orderProduct -> orderProduct.getProduct().getProductId(),
                        OrderProduct::getQuantity
                ));
    }

    @Named("mapProductQuantitiesToOrderProducts")
    default List<OrderProduct> mapProductQuantitiesToOrderProducts(Map<Long, Integer> productQuantities) {
        return productQuantities.entrySet().stream()
                .map(entry -> {
                    OrderProduct orderProduct = new OrderProduct();
                    Product product = new Product();
                    product.setProductId(entry.getKey()); // Impostare l'ID del prodotto
                    orderProduct.setProduct(product);
                    orderProduct.setQuantity(entry.getValue()); // Impostare la quantità
                    return orderProduct;
                })
                .collect(Collectors.toList());
    }
}
