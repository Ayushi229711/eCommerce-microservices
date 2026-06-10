package com.ayushi.order_service.kafka;

import com.ayushi.order_service.Entity.Order;
import com.ayushi.order_service.Repository.OrderRepository;
import com.ayushi.order_service.dto.InventoryFailedEvent;
import lombok.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryFailedConsumer {

    private final OrderRepository orderRepository;

    @KafkaListener(
            topics = "inventory-failed",
            groupId = "order-group"
    )
    public void consume(
            InventoryFailedEvent event
    ) {

        Order order =
                orderRepository
                        .findById(event.getOrderId())
                        .orElseThrow();

        order.setStatus("FAILED");

        orderRepository.save(order);

        System.out.println(
                "Order failed: "
                        + event.getOrderId()
        );
    }
}
