package it.gianmarco.demo.service;

import it.gianmarco.demo.entity.Product;
import it.gianmarco.demo.entity.Warehouse;
import it.gianmarco.demo.entity.WarehouseProduct;
import it.gianmarco.demo.entity.dto.WarehouseProductDto;
import it.gianmarco.demo.mapper.WarehouseProductMapper;
import it.gianmarco.demo.repository.ProductRepository;
import it.gianmarco.demo.repository.WarehouseProductRepository;
import it.gianmarco.demo.repository.WarehouseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class WarehouseProductService {

    private final WarehouseProductRepository warehouseProductRepository;
    private final WarehouseProductMapper warehouseProductMapper;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    public List<WarehouseProductDto> findAll() {
        List<WarehouseProduct> entities = warehouseProductRepository.findAll();
        return entities.stream()
                .map(warehouseProductMapper::toDto)
                .toList();
    }

    public List<Product> findProductsByWarehouseId(Long warehouseId) {
        List<Product> products = warehouseProductRepository.findByWarehouseId(warehouseId);
        log.info("Found {} products", products);
        return products;
    }

    private WarehouseProduct getWarehouseProduct(Warehouse warehouse, Long productId) {
        Optional<WarehouseProduct> warehouseProductOptional = warehouse.getProducts().stream()
                .filter(wp -> wp.getProduct().getProductId().equals(productId))
                .findFirst();

        if (warehouseProductOptional.isEmpty()) {
            throw new RuntimeException("Product not found in the specified warehouse");
        }

        return warehouseProductOptional.get();
    }


    public void removeProductQuantityFromWarehouse(Long warehouseId, Long productId, int quantityToRemove) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        WarehouseProduct warehouseProduct = getWarehouseProduct(warehouse, productId);

        if (warehouseProduct.getQuantity() < quantityToRemove) {
            throw new RuntimeException("Insufficient quantity in warehouse for product ID: " + productId);
        }

        warehouseProduct.setQuantity(warehouseProduct.getQuantity() - quantityToRemove);

        if (warehouseProduct.getQuantity() == 0) {
            warehouse.getProducts().remove(warehouseProduct);
            warehouseProductRepository.delete(warehouseProduct);
        }

        warehouseRepository.save(warehouse);

        productService.updateProductStockFromWarehouse(productId);
    }

    public void removeProductFromWarehouse(Long warehouseId, Long productId) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        WarehouseProduct warehouseProduct = getWarehouseProduct(warehouse, productId);
        warehouse.getProducts().remove(warehouseProduct);
        warehouseRepository.save(warehouse);

        productService.updateProductStockFromWarehouse(productId);
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
