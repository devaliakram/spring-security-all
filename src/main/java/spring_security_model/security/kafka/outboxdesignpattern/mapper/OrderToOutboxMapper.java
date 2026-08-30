package spring_security_model.security.kafka.outboxdesignpattern.mapper;


import org.springframework.stereotype.Component;
import spring_security_model.security.kafka.outboxdesignpattern.entity.Order;
import spring_security_model.security.kafka.outboxdesignpattern.entity.Outbox;
import tools.jackson.databind.ObjectMapper;

import java.util.Date;

@Component
public class OrderToOutboxMapper {
    /**
     *     @Id
     *     @GeneratedValue(strategy = GenerationType.IDENTITY)
     *     private Long id;
     *     private String aggregatedId;
     *     private String payload;
     *     private Date createdAt;
     *     private Boolean processed;
     * @param order
     * @return
     */
    public Outbox entityToOrder(Order order) {
        return Outbox.builder()
                .aggregatedId(order.getId().toString())
                .payload(new ObjectMapper().writeValueAsString(order))
                .createdAt(new Date())
                .processed(false).build();
    }
}
