package spring_security_model.security.kafka.outboxdesignpattern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_security_model.security.kafka.outboxdesignpattern.entity.Order;


public interface OrderRepository extends JpaRepository<Order,Long> {

}
