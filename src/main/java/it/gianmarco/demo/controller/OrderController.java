package it.gianmarco.demo.controller;

import it.gianmarco.demo.entity.Order;
import it.gianmarco.demo.entity.User;
import it.gianmarco.demo.service.OrderService;
import it.gianmarco.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> save(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(order));
    }

}
