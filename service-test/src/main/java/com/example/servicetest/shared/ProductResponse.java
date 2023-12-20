package com.example.servicetest.shared;

import lombok.Data;

@Data
public class ProductResponse {
    private int id;
    private String name;
    private String description;
    private float price;
    private int quantity;

}
