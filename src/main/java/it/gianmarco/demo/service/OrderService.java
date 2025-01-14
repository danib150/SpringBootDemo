package it.gianmarco.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.gianmarco.demo.entity.dto.OrderDto;
import it.gianmarco.demo.entity.Order;
import it.gianmarco.demo.entity.Product;
import it.gianmarco.demo.entity.User;
import it.gianmarco.demo.mapper.OrderMapper;
import it.gianmarco.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private final OrderMapper orderMapper;
    @Autowired private final UserService userService;
    @Autowired private final ProductService productService;

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

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


    @Transactional
    public Order createOrder(OrderDto orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);

        User user = userService.findById(orderDTO.getUserId());

        if (user != null) {
            order.setUser(user);
            order.setProducts(productService.findAllByIds(orderDTO.getProductIds()));

            List<Product> products = productService.findAllByIds(orderDTO.getProductIds());
            if (products.size() != orderDTO.getProductIds().size()) {
                throw new RuntimeException("Some products not found for the given IDs.");
            }

            return orderRepository.save(order);
        }

        throw new RuntimeException("User not found for ID: " + orderDTO.getUserId());
    }

    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toDto(order);
    }


}
