package com.tbless.inventoryManagementApp.dtos.response;

import com.tbless.inventoryManagementApp.data.models.Product;
import com.tbless.inventoryManagementApp.data.models.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponse {
    private String customerName;
    private String emailAddress;
    private String phoneNumber;
    private String uniqueId;
    private Product product;
    private int orderQuantity;
    private BigDecimal totalAmount;
    private String date;
    private PaymentStatus paymentStatus;
}
