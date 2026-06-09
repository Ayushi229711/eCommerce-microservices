package com.ayushi.order_service.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private Long orderId;
    private Long productId;
    private Integer quantity;
}
