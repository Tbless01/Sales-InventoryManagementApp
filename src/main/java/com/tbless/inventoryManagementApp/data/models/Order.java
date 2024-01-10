package com.tbless.inventoryManagementApp.data.models;

import com.tbless.inventoryManagementApp.data.models.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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
    private String uniqueId;
    @ManyToOne
    private Product product;
    private int orderQuantity;
    private BigDecimal totalAmount;
    private String dateOrdered;
    private PaymentStatus paymentStatus;
}
