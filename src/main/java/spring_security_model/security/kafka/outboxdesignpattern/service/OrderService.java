package spring_security_model.security.kafka.outboxdesignpattern.service;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import spring_security_model.security.kafka.outboxdesignpattern.dto.OrderRequestDto;
import spring_security_model.security.kafka.outboxdesignpattern.entity.Order;
import spring_security_model.security.kafka.outboxdesignpattern.entity.Outbox;
import spring_security_model.security.kafka.outboxdesignpattern.mapper.OrderDtoToEntity;
import spring_security_model.security.kafka.outboxdesignpattern.mapper.OrderToOutboxMapper;
import spring_security_model.security.kafka.outboxdesignpattern.repository.OrderRepository;
import spring_security_model.security.kafka.outboxdesignpattern.repository.OutboxRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderDtoToEntity orderDtoToEntity;
    private final OrderToOutboxMapper orderToOutboxMapper;


    private final OutboxRepository outboxRepository;


    public OrderService(OrderRepository orderRepository, OrderDtoToEntity orderDtoToEntity, OrderToOutboxMapper orderToOutboxMapper, OutboxRepository outboxRepository) {
        this.orderRepository = orderRepository;
        this.orderDtoToEntity = orderDtoToEntity;
        this.orderToOutboxMapper = orderToOutboxMapper;
        this.outboxRepository = outboxRepository;
    }

    @Transactional
    public Order createOrder(OrderRequestDto orderRequestDto) {
        Order order = orderDtoToEntity.toEntity(orderRequestDto);

        order = orderRepository.save(order);

        //as per @Transaction, if any failure occurs we have to rollback
        System.out.println(10/0);
        // I need to commit the code in outbox table once order is commited to order table
        Outbox outbox = orderToOutboxMapper.entityToOrder(order);
        outboxRepository.save(outbox);
        return order;
    }
}
