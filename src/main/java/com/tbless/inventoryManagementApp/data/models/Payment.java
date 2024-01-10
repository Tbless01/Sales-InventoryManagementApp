package com.tbless.inventoryManagementApp.data.models;

import com.tbless.inventoryManagementApp.data.models.enums.PaymentMethod;
import com.tbless.inventoryManagementApp.data.models.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment {
    @Id
    private String id;
    private String orderId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private String senderId;
    private Status orderStatus;
    private String paidAt;
    private LocalDateTime createdAt;
    private String channel;
}
