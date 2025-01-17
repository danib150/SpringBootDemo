package it.gianmarco.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.gianmarco.demo.entity.dto.OrderDto;
import it.gianmarco.demo.entity.Order;
import it.gianmarco.demo.entity.OrderProduct;
import it.gianmarco.demo.entity.Product;
import it.gianmarco.demo.entity.User;
import it.gianmarco.demo.entity.dto.OrderProductDto;
import it.gianmarco.demo.entity.enums.OrderStatusEnum;
import it.gianmarco.demo.mapper.OrderMapper;
import it.gianmarco.demo.mapper.OrderProductMapper;
import it.gianmarco.demo.repository.OrderProductRepository;
import it.gianmarco.demo.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    @Autowired private final OrderRepository orderRepository;
    @Autowired private final OrderProductRepository orderProductRepository;
    @Autowired private final OrderMapper orderMapper;
    @Autowired private final OrderProductMapper orderProductMapper;
    @Autowired private final UserService userService;
    @Autowired private final ProductService productService;

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public List<Order> findAll() {
        List<Order> orders = orderRepository.findAll();
        try {
            logger.info("OrderService | findAll: {}", new ObjectMapper().writeValueAsString(orders));
        } catch (JsonProcessingException e) {
            logger.warn("Impossibile convertire l'oggetto in stringa!");
        }
        return orders;
    }

    @Transactional
    public Order createOrder(OrderDto orderDTO) {
        // Trova l'utente
        User user = userService.findById(orderDTO.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found for ID: " + orderDTO.getUserId());
        }

        // Ottieni la mappa di ID prodotto -> quantità
        Map<Long, Integer> productQuantities = orderDTO.getProductQuantities();

        // Trova i prodotti nel database tramite gli ID
        List<Product> products = productService.findAllByIds(new ArrayList<>(productQuantities.keySet()));
        if (products.size() != productQuantities.size()) {
            throw new RuntimeException("Some products not found for the given IDs.");
        }

        // Verifica la disponibilità dei prodotti nel magazzino
        for (Product product : products) {
            Integer requestedQuantity = productQuantities.get(product.getProductId());
            if (requestedQuantity == null || product.getStockQuantity() < requestedQuantity) {
                throw new RuntimeException("Product " + product.getProductName() + " is out of stock or insufficient quantity.");
            }
        }

        Order order = new Order();
        order.setUser(user);
        order.setCreationDate(orderDTO.getCreationDate());
        order.setUpdateDate(orderDTO.getUpdateDate());
        order.setTotalPrice(calculateTotalPrice(products, productQuantities));
        order.setOrderStatus(OrderStatusEnum.IN_PROGRESS);

        order = orderRepository.save(order);

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (Product product : products) {
            Integer quantity = productQuantities.get(product.getProductId());

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setQuantity(quantity);

            int newStockQuantity = product.getStockQuantity() - quantity;
            product.setStockQuantity(newStockQuantity);

            orderProducts.add(orderProduct);
        }

        orderProductRepository.saveAll(orderProducts);
        order.setOrderProducts(orderProducts);

        productService.saveAll(products);

        return order;
    }



    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toDto(order);
    }


    private Double calculateTotalPrice(List<Product> products, Map<Long, Integer> productQuantities) {
        double total = 0;
        for (Product product : products) {
            Integer quantity = productQuantities.get(product.getProductId());
            if (quantity != null) {
                total += product.getPrice() * quantity;
            }
        }
        return total;
    }


    public List<OrderProductDto> getOrderProducts(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderProduct> orderProducts = orderProductRepository.findByOrder(order);
        return orderProducts.stream()
                .map(orderProductMapper::toDto)
                .collect(Collectors.toList());
    }

}
