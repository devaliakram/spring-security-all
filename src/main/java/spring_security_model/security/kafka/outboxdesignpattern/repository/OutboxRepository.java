package spring_security_model.security.kafka.outboxdesignpattern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_security_model.security.kafka.outboxdesignpattern.entity.Outbox;

public interface OutboxRepository extends JpaRepository<Outbox, Long> {

}
