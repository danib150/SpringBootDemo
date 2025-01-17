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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Tag(name = "Order Controller")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody OrderDto order) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(order));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

}
