package com.ayushi.inventory_service.kafka;

import com.ayushi.inventory_service.Service.InventoryService;
import com.ayushi.inventory_service.dto.InventoryFailedEvent;
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

//    @KafkaListener(
//            topics = "order-created",
//            groupId = "inventory-group"
//    )
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

//    @KafkaListener(
//            topics = "order-created",
//            groupId = "inventory-group"
//    )
//    public void consume(OrderCreatedEvent event) {
//
//        inventoryService.reduceStock(
//                event.getProductId(),
//                event.getQuantity()
//        );
//
//        InventoryReservedEvent reservedEvent =
//                new InventoryReservedEvent(
//                        event.getOrderId(),
//                        event.getProductId()
//                );
//
//        inventoryProducer.publish(reservedEvent);
//
//        System.out.println(
//                "Inventory updated for order "
//                        + event.getOrderId()
//        );
//    }

    @KafkaListener(
            topics = "order-created",
            groupId = "inventory-group"
    )
    public void consume(OrderCreatedEvent event) {

        try {

            inventoryService.reduceStock(
                    event.getProductId(),
                    event.getQuantity()
            );

            InventoryReservedEvent reserved =
                    new InventoryReservedEvent(
                            event.getOrderId(),
                            event.getProductId()
                    );

            inventoryProducer.publish(reserved);

            System.out.println(
                    "Inventory reserved for order "
                            + event.getOrderId()
            );

        } catch (RuntimeException ex) {

            InventoryFailedEvent failed =
                    new InventoryFailedEvent(
                            event.getOrderId(),
                            event.getProductId(),
                            ex.getMessage()
                    );

            inventoryProducer.publishFailure(failed);

            System.out.println(
                    "Inventory failed for order "
                            + event.getOrderId()
            );
        }
    }
}
