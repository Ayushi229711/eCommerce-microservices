package com.ayushi.inventory_service.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryReservedEvent {
    private Long orderId;
    private Long productId;
}
