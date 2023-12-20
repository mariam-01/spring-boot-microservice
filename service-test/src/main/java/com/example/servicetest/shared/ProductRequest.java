package com.example.servicetest.shared;

import lombok.Data;

@Data
public class ProductRequest {
    private int id;
    private String name;
    private String description;
    private float price;
    private int quantity;
}
