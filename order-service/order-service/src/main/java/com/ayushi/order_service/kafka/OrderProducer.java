package com.ayushi.order_service.kafka;

import com.ayushi.order_service.dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public void publish(OrderCreatedEvent event) {
        System.out.println("Publishing Event: " + event);


        kafkaTemplate.send(
                "order-created",
                event
        );
    }
}
