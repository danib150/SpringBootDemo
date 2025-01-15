package it.gianmarco.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.gianmarco.demo.entity.Product;
import it.gianmarco.demo.entity.Warehouse;
import it.gianmarco.demo.entity.WarehouseProduct;
import it.gianmarco.demo.entity.dto.WarehouseProductDto;
import it.gianmarco.demo.service.WarehouseProductService;
import it.gianmarco.demo.service.WarehouseService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/warehouse-product")
@RequiredArgsConstructor
@Tag(name = "Warehouse Product Controller")
public class WarehouseProductController {

    private final WarehouseProductService warehouseProductService;

    @GetMapping("/{warehouseId}/products")
    public ResponseEntity<List<WarehouseProduct>> getAllProducts(@PathVariable("warehouseId") Long warehouseId) {
        return ResponseEntity.ok(warehouseProductService.findProductsByWarehouseId(warehouseId));

    }
    @PostMapping("/{warehouseId}/add")
    public ResponseEntity<?> addWarehouseProduct(@PathVariable("warehouseId") Long warehouseId, @RequestBody WarehouseProductDto warehouseProductDto) {
        try {
            return ResponseEntity.ok(warehouseProductService.save(warehouseId,warehouseProductDto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
