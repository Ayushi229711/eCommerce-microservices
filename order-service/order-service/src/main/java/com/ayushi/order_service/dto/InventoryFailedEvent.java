package com.ayushi.order_service.dto;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryFailedEvent {

    private Long orderId;
    private Long productId;
    private String reason;
}
