package it.gianmarco.demo.service;

import it.gianmarco.demo.entity.Product;
import it.gianmarco.demo.entity.Warehouse;
import it.gianmarco.demo.entity.WarehouseProduct;
import it.gianmarco.demo.repository.ProductRepository;
import it.gianmarco.demo.repository.WarehouseProductRepository;
import it.gianmarco.demo.repository.WarehouseRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final WarehouseProductRepository warehouseProductRepository;

    public Warehouse save(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public String updateProducts(Long productId, Integer quantity) {
        Warehouse warehouse = warehouseRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        WarehouseProduct warehouseProduct = warehouseProductRepository
                .findByWarehouseAndProduct(warehouse, product)
                .orElse(WarehouseProduct.builder()
                        .warehouse(warehouse)
                        .product(product)
                        .quantity(0)
                        .build());

        warehouseProduct.setQuantity(warehouseProduct.getQuantity() + quantity);

        warehouseProductRepository.save(warehouseProduct);

        return "Product added/updated successfully";

    }

    public List<WarehouseProduct> getAllProducts() {
        Warehouse warehouse = warehouseRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        return warehouse.getProducts();
    }
}
