package it.gianmarco.demo.service;

import it.gianmarco.demo.entity.WarehouseProduct;
import it.gianmarco.demo.entity.dto.WarehouseDto;
import it.gianmarco.demo.repository.WarehouseProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WarehouseProductService {

    private final WarehouseProductRepository warehouseProductRepository;
    public List<WarehouseProduct> findAll() {
        return warehouseProductRepository.findAll();
    }

    public WarehouseProduct findById(Long id) {
        return warehouseProductRepository.findById(id).orElse(null);
    }

    public WarehouseProduct save(WarehouseDto warehouseProduct) {
        return warehouseProductRepository.save(warehouseProduct);
    }
}
