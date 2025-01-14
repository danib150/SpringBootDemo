package it.gianmarco.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.gianmarco.demo.entity.Warehouse;
import it.gianmarco.demo.entity.WarehouseProduct;
import it.gianmarco.demo.entity.dto.WarehouseProductDto;
import it.gianmarco.demo.service.WarehouseService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/warehouse-product")
@RequiredArgsConstructor
@Tag(name = "Warehouse Product Controller")
public class WarehouseProductController {

    @GetMapping("/{warehouseId}/products")
    public ResponseEntity<?> getAllProducts(@PathParam("warehouseId") Long warehouseId) {
        return Warehouse.
    }

    @PostMapping("/{warehouseId}/add")
    public ResponseEntity<Warehouse> addWarehouseProduct(@PathParam("warehouseId") Long warehouseId,
                                                         @RequestBody WarehouseProductDto warehouseProductDto) {

    }
}
