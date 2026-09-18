package spring_kafka_consumer_puller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderPuller {

    public static void main(String[] args) {
        SpringApplication.run(OrderPuller.class, args);
    }

}
