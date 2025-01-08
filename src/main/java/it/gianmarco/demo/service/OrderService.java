package it.gianmarco.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.gianmarco.demo.entity.Order;
import it.gianmarco.demo.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    Logger logger = LoggerFactory.getLogger(ProductService.class);

    public List<Order> findAll() {
        List<Order> orders = orderRepository.findAll();
        try {
            logger.info("userService | findAll: {}",new ObjectMapper().writeValueAsString(orders));
        } catch (JsonProcessingException e) {
            logger.warn("Impossibile convertire l'oggetto in stringa!");
        }
        return orders;
    }
    public Order save(Order order) {
        return orderRepository.save(order);
    }

}
