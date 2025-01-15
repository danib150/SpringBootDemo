package it.gianmarco.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.gianmarco.demo.entity.Product;
import it.gianmarco.demo.entity.dto.ProductDto;
import it.gianmarco.demo.mapper.ProductMapper;
import it.gianmarco.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        try {
            logger.info("ProductService | findAll: {}", new ObjectMapper().writeValueAsString(products));
        } catch (JsonProcessingException e) {
            logger.error("Failed to convert product list to JSON: {}", e.getMessage());
        }
        return products;
    }

    @Transactional
    public ProductDto saveProduct(ProductDto productDto) {
        if (productDto.getProductName() == null || productDto.getProductName().isEmpty()) {
            throw new RuntimeException("Product name cannot be null or empty");
        }
        Product product = productMapper.toEntity(productDto);

        Product savedProduct = productRepository.save(product);

        return productMapper.toDto(savedProduct);
    }

    @Transactional
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        boolean isUpdated = false;

        if (productDto.getProductName() != null && !productDto.getProductName().equals(existingProduct.getProductName())) {
            existingProduct.setProductName(productDto.getProductName());
            isUpdated = true;
        }

        if (productDto.getProductDescription() != null && !productDto.getProductDescription().equals(existingProduct.getProductDescription())) {
            existingProduct.setProductDescription(productDto.getProductDescription());
            isUpdated = true;
        }

        if (productDto.getPrice() > 0 && productDto.getPrice() != existingProduct.getPrice()) {
            existingProduct.setPrice(productDto.getPrice());
            isUpdated = true;
        }

        if (!isUpdated) {
            logger.warn("No update was made as the product data is the same.");
            return null; // Se nessuna modifica è stata fatta, ritorna null
        }

        // Salva l'entità aggiornata nel database
        Product updatedProduct = productRepository.save(existingProduct);

        // Mappa l'entità aggiornata al DTO
        return productMapper.toDto(updatedProduct);
    }

    public void remove(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            logger.warn("Product with ID {} not found!", id);
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
        logger.info("Product with ID {} has been removed", id);
    }

    public List<Product> findAllByIds(List<Long> productIds) {
        List<Product> products = productRepository.findByProductIdIn(productIds);
        if (products.isEmpty()) {
            logger.warn("No products found for the given IDs: {}", productIds);
            throw new RuntimeException("No products found");
        }
        return products;
    }
}
