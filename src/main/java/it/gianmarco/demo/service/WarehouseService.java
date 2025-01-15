package it.gianmarco.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.gianmarco.demo.entity.Product;
import it.gianmarco.demo.entity.User;
import it.gianmarco.demo.entity.Warehouse;
import it.gianmarco.demo.entity.WarehouseProduct;
import it.gianmarco.demo.entity.dto.WarehouseDto;
import it.gianmarco.demo.mapper.WarehouseMapper;
import it.gianmarco.demo.repository.ProductRepository;
import it.gianmarco.demo.repository.WarehouseProductRepository;
import it.gianmarco.demo.repository.WarehouseRepository;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private WarehouseMapper warehouseMapper;

    public List<Warehouse> findAll() {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        try {
            log.info("userService | findAll: {}", new ObjectMapper().writeValueAsString(warehouses));
        } catch (JsonProcessingException e) {
            log.warn("Impossibile convertire l'oggetto in stringa!");
        }
        return warehouses;
    }

    public Warehouse saveWarehouseName(WarehouseDto warehouseDto) {
        Warehouse warehouse = warehouseMapper.toEntity(warehouseDto);
        return warehouseRepository.save(warehouse);
    }

    public Warehouse updateWarehouseName(Long id, WarehouseDto warehouseDto) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        if (optionalWarehouse.isPresent()) {
            Warehouse warehouse = optionalWarehouse.get();
            warehouse.setName(warehouseDto.getName());
            return warehouseRepository.save(warehouse);
        } else {
            throw new RuntimeException("Warehouse not found");
        }
    }

    public List<WarehouseProduct> getAllProducts() {
        Warehouse warehouse = warehouseRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        return warehouse.getProducts();
    }
}
