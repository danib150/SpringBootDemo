package it.gianmarco.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.gianmarco.demo.entity.Product;
import it.gianmarco.demo.entity.Warehouse;
import it.gianmarco.demo.entity.WarehouseProduct;
import it.gianmarco.demo.entity.dto.ProductDto;
import it.gianmarco.demo.entity.dto.WarehouseProductDto;
import it.gianmarco.demo.service.WarehouseProductService;
import it.gianmarco.demo.service.WarehouseService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/warehouse-product")
@RequiredArgsConstructor
@Tag(name = "Warehouse Product Controller")
public class WarehouseProductController {

    private final WarehouseProductService warehouseProductService;

    @GetMapping("/{warehouseId}/products")
    public ResponseEntity<List<Product>> getAllProducts(@PathVariable("warehouseId") Long warehouseId) {
        return ResponseEntity.ok(warehouseProductService.findProductsByWarehouseId(warehouseId));

    }

   @DeleteMapping("/{warehouseId}/remove/{productId}")
   public ResponseEntity<?> removeProduct(@PathVariable("warehouseId") Long warehouseId,  @PathVariable("productId") Long productId, @RequestParam Integer quantity) {
            try {
                if (Objects.isNull(quantity)) {
                    warehouseProductService.removeProductFromWarehouse(warehouseId, productId);
                    return ResponseEntity.ok().build();
                }
                warehouseProductService.removeProductQuantityFromWarehouse(warehouseId, productId, quantity);
            } catch (EntityNotFoundException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }



        return ResponseEntity.ok().build();

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
