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

import java.util.List;
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

        // Trova i prodotti
        List<Product> products = productService.findAllByIds(orderDTO.getProductIds());
        if (products.size() != orderDTO.getProductIds().size()) {
            throw new RuntimeException("Some products not found for the given IDs.");
        }

        // Crea l'ordine
        Order order = new Order();
        order.setUser(user);
        order.setCreationDate(orderDTO.getCreationDate());
        order.setUpdateDate(orderDTO.getUpdateDate());
        order.setTotalPrice(calculateTotalPrice(products));
        order.setOrderStatus(OrderStatusEnum.IN_PROGRESS);

        // Salva l'ordine
        order = orderRepository.save(order);

        // Crea le relazioni OrderProduct
        Order finalOrder = order;
        List<OrderProduct> orderProducts = products.stream()
                .map(product -> {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setOrder(finalOrder);
                    orderProduct.setProduct(product);
                    orderProduct.setQuantity(1); // Puoi personalizzare la quantità da `orderDTO`
                    return orderProduct;
                })
                .collect(Collectors.toList());

        // Salva le relazioni
        orderProductRepository.saveAll(orderProducts);
        order.setOrderProducts(orderProducts);

        return order;
    }

    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toDto(order);
    }

    private Double calculateTotalPrice(List<Product> products) {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
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
