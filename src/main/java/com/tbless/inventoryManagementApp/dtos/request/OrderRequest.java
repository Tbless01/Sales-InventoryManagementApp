package com.tbless.inventoryManagementApp.dtos.request;

import com.tbless.inventoryManagementApp.data.models.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderRequest {

    private String customerName;
    private String emailAddress;
    private String phoneNumber;
    private String orderId;
    @ManyToOne
    private Product product;
    private int orderQuantity;
    private LocalDateTime dateOrdered;
}
