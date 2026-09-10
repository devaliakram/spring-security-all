package com.example.orders.service;


import com.example.orders.dto.OrderReqDTO;
import com.example.orders.entity.Order;
import com.example.orders.entity.Outbox;
import com.example.orders.mapper.OrderDtoToEntity;
import com.example.orders.mapper.OutboxDtoToEntity;
import com.example.orders.repository.OrderRepository;
import com.example.orders.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {


    private final OrderRepository orderRepository;

    private final OutboxRepository outboxRepository;

    private final OrderDtoToEntity orderDtoToEntity;

    private final OutboxDtoToEntity outboxDtoToEntity;

    public OrderService(OrderRepository orderRepository, OutboxRepository outboxRepository, OrderDtoToEntity orderDtoToEntity, OutboxDtoToEntity outboxDtoToEntity) {
        this.orderRepository = orderRepository;
        this.outboxRepository = outboxRepository;
        this.orderDtoToEntity = orderDtoToEntity;
        this.outboxDtoToEntity = outboxDtoToEntity;
    }


    public Order create(OrderReqDTO reqDTO) throws JsonProcessingException {

        Order order = orderDtoToEntity.toEntity(reqDTO);

        Order save = orderRepository.save(order);

        // saving to outbox

        Outbox outbox = outboxDtoToEntity.toEntity(order);

        outboxRepository.save(outbox);

        return save;

    }

    public List<Order> all() {
        return orderRepository.findAll();
    }

    public Order one(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }


}
