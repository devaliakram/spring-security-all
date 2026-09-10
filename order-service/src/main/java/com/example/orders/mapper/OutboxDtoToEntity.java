package com.example.orders.mapper;


import com.example.orders.entity.Order;
import com.example.orders.entity.Outbox;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OutboxDtoToEntity {


    public Outbox toEntity(Order order) throws JsonProcessingException {
        return Outbox.builder().
                aggregateId(order.getId().toString()).
                payload(new ObjectMapper().writeValueAsString(order)).
                createdAt(new Date()).
                processed(false).
                build();
    }
}
