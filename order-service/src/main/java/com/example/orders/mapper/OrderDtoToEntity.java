package com.example.orders.mapper;

import com.example.orders.dto.OrderReqDTO;
import com.example.orders.entity.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderDtoToEntity {

    /**
     *    private String name;
     *     @NotBlank
     *     private String customerId;
     *     @NotBlank
     *     private String productType;
     *     @NotNull
     *     @Min(1)
     *     private Integer quantity;
     *     @NotNull
     *     @DecimalMin("0.01")
     *     private BigDecimal price;
     * @param orderReqDTO
     * @return
     */

    public Order toEntity(OrderReqDTO orderReqDTO) {

        return Order.builder().name(orderReqDTO.getName())
                .customerId(orderReqDTO.getCustomerId())
                .price(orderReqDTO.getPrice())
                .quantity(orderReqDTO.getQuantity())
                .orderDate(new Date())
                .productType(orderReqDTO.getProductType())
                .build();


    }
}
