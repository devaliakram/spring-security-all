package com.example.orders.controller;

import com.example.orders.dto.OrderReqDTO;
import com.example.orders.entity.Order;
import com.example.orders.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {


    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> create(@Valid @RequestBody OrderReqDTO orderReqDTO) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(orderReqDTO));
    }

    @GetMapping("/all")
    public List<Order> all() {
        return orderService.all();
    }

    @GetMapping("/{id}")
    public Order one(@PathVariable Long id) {
        return orderService.one(id);
    }
}