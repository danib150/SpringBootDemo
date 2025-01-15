package it.gianmarco.demo.mapper;

import it.gianmarco.demo.entity.Product;
import it.gianmarco.demo.entity.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDto);
}
