package com.ayushi.inventory_service.kafka;

import com.ayushi.inventory_service.dto.InventoryFailedEvent;
import com.ayushi.inventory_service.dto.InventoryReservedEvent;
import lombok.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryProducer {

    private final KafkaTemplate<String, InventoryReservedEvent> kafkaTemplate;

    public void publish(InventoryReservedEvent event) {
        kafkaTemplate.send("inventory-reserved", event);
        System.out.println("Published InventoryReservedEvent for order "
                + event.getOrderId());
    }

    private final KafkaTemplate<String, InventoryFailedEvent> failedKafkaTemplate;
    public void publishFailure(
            InventoryFailedEvent event
    ) {
        failedKafkaTemplate.send(
                "inventory-failed",
                event
        );
    }
}