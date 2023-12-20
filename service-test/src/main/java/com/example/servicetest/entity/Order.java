package com.example.servicetest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_order")
public class Order {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String orderNumber;
        private Double totalPrice;
        @OneToMany(cascade = CascadeType.ALL)
        private List<OrderLineItems> orderLineItemsList;

}
