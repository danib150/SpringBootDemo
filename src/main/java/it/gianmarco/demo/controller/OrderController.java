package it.gianmarco.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import it.gianmarco.demo.entity.Order;
import it.gianmarco.demo.entity.dto.OrderDto;
import it.gianmarco.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Tag(name = "Order Controller")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> save(@RequestBody OrderDto order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(order));
    }

}
