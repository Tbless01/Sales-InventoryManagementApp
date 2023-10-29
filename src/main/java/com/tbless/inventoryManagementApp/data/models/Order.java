package com.tbless.inventoryManagementApp.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String emailAddress;
    private String phoneNumber;
    private String orderId;
    @ManyToOne
    private Product product;
    private LocalDateTime dateOrdered;
}
