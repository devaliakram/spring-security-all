package spring_kafka_consumer_puller.service;


import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import spring_kafka_consumer_puller.entity.Outbox;
import spring_kafka_consumer_puller.publish.MessagePublisher;
import spring_kafka_consumer_puller.repository.OutboxRepository;

import java.util.List;

@Service
@EnableScheduling
public class OrderPullerService {

    private final OutboxRepository outboxRepository;

    private final MessagePublisher messagePublisher;

    public OrderPullerService(OutboxRepository outboxRepository, MessagePublisher messagePublisher) {
        this.outboxRepository = outboxRepository;
        this.messagePublisher = messagePublisher;
    }


    @Scheduled(fixedRate = 60000)
    public List<Outbox> pollOutboxMessageAndThenPublish() {

        //s1- fetch the unprocessed records
        List<Outbox> unprocessedRecords = outboxRepository.findByProcessedFalse();
        //s2-then published the same records to kafka
        unprocessedRecords.forEach(outbox -> {
            try {
                messagePublisher.publish(outbox.getPayload());
                outbox.setProcessed(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return unprocessedRecords;
    }
}
