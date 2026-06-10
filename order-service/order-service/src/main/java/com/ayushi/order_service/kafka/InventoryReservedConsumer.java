package com.ayushi.order_service.kafka;
;
import com.ayushi.order_service.Entity.Order;
import com.ayushi.order_service.Repository.OrderRepository;
import com.ayushi.order_service.dto.InventoryReservedEvent;
import lombok.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryReservedConsumer {

    private final OrderRepository orderRepository;

    @KafkaListener(
            topics = "inventory-reserved",
            groupId = "order-group"
    )
    public void consume(InventoryReservedEvent event) {

        Order order = orderRepository
                .findById(event.getOrderId())
                .orElseThrow();

        order.setStatus("CONFIRMED");

        orderRepository.save(order);

        System.out.println(
                "Order confirmed: "
                        + event.getOrderId()
        );
    }
}
