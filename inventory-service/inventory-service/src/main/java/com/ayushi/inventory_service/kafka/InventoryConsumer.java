package com.ayushi.inventory_service.kafka;

import com.ayushi.inventory_service.Service.InventoryService;
import com.ayushi.inventory_service.dto.InventoryReservedEvent;
import com.ayushi.inventory_service.dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class InventoryConsumer {

    private final InventoryService inventoryService;
    private final InventoryProducer inventoryProducer;

    @KafkaListener(
            topics = "order-created",
            groupId = "inventory-group"
    )
//    public void consume(OrderCreatedEvent event) {
//
//        inventoryService.reduceStock(
//                event.getProductId(),
//                event.getQuantity()
//        );
//
//        System.out.println(
//                "Inventory updated for order "
//                        + event.getOrderId()
//        );
//
//    }

    @KafkaListener(
            topics = "order-created",
            groupId = "inventory-group"
    )
    public void consume(OrderCreatedEvent event) {

        inventoryService.reduceStock(
                event.getProductId(),
                event.getQuantity()
        );

        InventoryReservedEvent reservedEvent =
                new InventoryReservedEvent(
                        event.getOrderId(),
                        event.getProductId()
                );

        inventoryProducer.publish(reservedEvent);

        System.out.println(
                "Inventory updated for order "
                        + event.getOrderId()
        );
    }
}
