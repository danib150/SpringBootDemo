package it.gianmarco.demo.service;

import it.gianmarco.demo.entity.Order;
import it.gianmarco.demo.entity.dto.OrderDto;
import it.gianmarco.demo.mapper.OrderMapper;
import it.gianmarco.demo.mapper.OrderProductMapper;
import it.gianmarco.demo.repository.OrderProductRepository;
import it.gianmarco.demo.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderProductRepository orderProductRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private OrderProductMapper orderProductMapper;
    @Mock
    private UserService userService;
    @Mock
    private ProductService productService;

    @Test
    public void createOrderUserNotFoundShouldThrowException() {
        // WHEN
        doReturn(null).when(userService).findById(anyLong());

        // THEN
        try {
            orderService.createOrder(OrderDto.builder().userId(1L).build());
        } catch (RuntimeException e) {
            log.warn("userNotFoundShouldThrowException : {}", e.getMessage());
            assert e.getMessage().contains("1");
        }
    }

}
