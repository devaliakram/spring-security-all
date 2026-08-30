package spring_kafka_consumer_puller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_kafka_consumer_puller.entity.Outbox;

import java.util.List;


public interface OutboxRepository extends JpaRepository<Outbox, Long> {

    //unprocessed records
    List<Outbox> findByProcessedFalse();

}
