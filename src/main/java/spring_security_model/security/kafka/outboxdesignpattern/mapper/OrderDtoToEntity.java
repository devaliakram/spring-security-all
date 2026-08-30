package spring_security_model.security.kafka.outboxdesignpattern.mapper;


import org.springframework.stereotype.Component;
import spring_security_model.security.kafka.outboxdesignpattern.dto.OrderRequestDto;
import spring_security_model.security.kafka.outboxdesignpattern.entity.Order;

import java.util.Date;

@Component
public class OrderDtoToEntity {

    public Order toEntity(OrderRequestDto orderRequestDto) {

        return Order.builder().name(orderRequestDto.getName())
                .customerId(orderRequestDto.getCustomerId())
                .productType(orderRequestDto.getProductType())
                .quantity(orderRequestDto.getQuantity())
                .price(orderRequestDto.getPrice())
                .orderDate(new Date())
                .build();
    }


}
