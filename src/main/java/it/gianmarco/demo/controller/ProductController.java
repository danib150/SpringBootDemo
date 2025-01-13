package it.gianmarco.demo.controller;

import it.gianmarco.demo.entity.Product;
import it.gianmarco.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PatchMapping
    public ResponseEntity<Product> update(@RequestBody Product product) {
        if (Objects.isNull(product.getProductId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(productService.save(product));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable Long id) {
        try {
            productService.remove(id);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            log.warn("Error deleting product: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }
}
