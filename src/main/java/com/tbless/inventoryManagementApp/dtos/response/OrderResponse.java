package com.tbless.inventoryManagementApp.dtos.response;

import com.tbless.inventoryManagementApp.data.models.Product;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponse {
    private String customerName;
    private String emailAddress;
    private String phoneNumber;
    private String orderId;
    private Product product;
    private LocalDateTime date;
}
