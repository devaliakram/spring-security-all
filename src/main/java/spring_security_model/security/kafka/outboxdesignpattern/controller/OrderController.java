package spring_security_model.security.kafka.outboxdesignpattern.controller;

import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_security_model.security.kafka.outboxdesignpattern.dto.OrderRequestDto;
import spring_security_model.security.kafka.outboxdesignpattern.entity.Order;
import spring_security_model.security.kafka.outboxdesignpattern.service.OrderService;


@RestController
@RequestMapping("/order/v1")
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDto orderRequestDto) {

        Order order = orderService.createOrder(orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
