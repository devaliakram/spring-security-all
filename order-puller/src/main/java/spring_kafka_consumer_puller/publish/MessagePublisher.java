package spring_kafka_consumer_puller.publish;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class MessagePublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${order.poller.topic.name}")
    private String topicName;

    public MessagePublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(String payload) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, payload);

        future.whenComplete((results, exception) -> {
            if (exception == null) {
                System.out.println( payload +results.getRecordMetadata().offset());
            } else {
                System.out.println(payload + exception.getMessage());
            }
        });

    }


}
