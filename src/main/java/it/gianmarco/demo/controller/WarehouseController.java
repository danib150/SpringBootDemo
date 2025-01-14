package it.gianmarco.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.gianmarco.demo.entity.Product;
import it.gianmarco.demo.entity.Warehouse;
import it.gianmarco.demo.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/warehouse")
@RequiredArgsConstructor
@Tag(name = "Warehouse Controller")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        try {
            return ResponseEntity.ok(warehouseService.getAllProducts());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Warehouse> addWarehouse(@RequestBody Warehouse warehouse) {
        return ResponseEntity.ok(warehouseService.save(warehouse));
    }
}
