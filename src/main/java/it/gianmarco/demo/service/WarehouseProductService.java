package it.gianmarco.demo.service;

import it.gianmarco.demo.entity.Product;
import it.gianmarco.demo.entity.Warehouse;
import it.gianmarco.demo.entity.WarehouseProduct;
import it.gianmarco.demo.entity.dto.WarehouseDto;
import it.gianmarco.demo.entity.dto.WarehouseProductDto;
import it.gianmarco.demo.mapper.WarehouseProductMapper;
import it.gianmarco.demo.repository.ProductRepository;
import it.gianmarco.demo.repository.WarehouseProductRepository;
import it.gianmarco.demo.repository.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WarehouseProductService {

    private final WarehouseProductRepository warehouseProductRepository;
    private final WarehouseProductMapper warehouseProductMapper;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    public List<WarehouseProductDto> findAll() {
        List<WarehouseProduct> entities = warehouseProductRepository.findAll();
        return entities.stream()
                .map(warehouseProductMapper::toDto)
                .toList();
    }

    public List<WarehouseProduct> findProductsByWarehouseId(Long warehouseId) {
        return warehouseProductRepository.findByWarehouseId(warehouseId);
    }


    public WarehouseProductDto findById(Long id) {
        WarehouseProduct entity = warehouseProductRepository.findById(id).orElse(null);
        return entity != null ? warehouseProductMapper.toDto(entity) : null;
    }

    public WarehouseProduct save(Long warehouseID, WarehouseProductDto warehouseProductDto) {

        Warehouse warehouse = warehouseRepository.findById(warehouseID)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        Product product = productRepository.findById(warehouseProductDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        WarehouseProduct warehouseProduct = warehouseProductMapper.toEntity(warehouseProductDto);
        warehouseProduct.setWarehouse(warehouse);
        warehouseProduct.setProduct(product);

        return warehouseProductRepository.save(warehouseProduct);
    }
}
