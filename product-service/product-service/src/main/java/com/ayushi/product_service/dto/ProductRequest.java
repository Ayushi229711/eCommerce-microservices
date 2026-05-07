package com.ayushi.product_service.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private double price;
    private int stock;
    private String category;
}
