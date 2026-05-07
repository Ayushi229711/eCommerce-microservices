package com.ayushi.product_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private double price;
    private int stock;
    private String category;
}
