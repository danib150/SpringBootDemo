package it.gianmarco.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.gianmarco.demo.entity.Product;
import it.gianmarco.demo.entity.dto.ProductDto;
import it.gianmarco.demo.mapper.ProductMapper;
import it.gianmarco.demo.repository.ProductRepository;
import it.gianmarco.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Products Controller")
public class ProductController {

    @Autowired
    private final ProductService service;
    @Autowired
    private final ProductService productService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productRepository.save(productMapper.toEntity(productDto)));
    }

    @PatchMapping("{productId}")
    public ResponseEntity<ProductDto> update(@PathVariable Long productId, @RequestBody ProductDto productDto) {
        ProductDto updatedProductDto = productService.updateProduct(productId, productDto);

        if (updatedProductDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok(updatedProductDto);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable Long id) {
        try {
            productService.remove(id);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            log.warn("Error deleting product: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }
}
