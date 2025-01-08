package it.gianmarco.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.gianmarco.demo.entity.Product;
import it.gianmarco.demo.entity.User;
import it.gianmarco.demo.repository.ProductRepository;
import it.gianmarco.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    Logger logger = LoggerFactory.getLogger(ProductService.class);

    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        try {
            logger.info("userService | findAll: {}",new ObjectMapper().writeValueAsString(products));
        } catch (JsonProcessingException e) {
            logger.warn("Impossibile convertire l'oggetto in stringa!");
        }
        return products;
    }
    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void remove(Long id) {
        if (!productRepository.existsById(id)) {
            logger.warn("Product with this id, not found!");
        }
        productRepository.deleteById(id);
    }



}
