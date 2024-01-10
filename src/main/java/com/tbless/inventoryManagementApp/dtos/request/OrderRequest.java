package com.tbless.inventoryManagementApp.dtos.request;

import com.tbless.inventoryManagementApp.data.models.enums.PaymentStatus;
import com.tbless.inventoryManagementApp.data.models.Product;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderRequest {

    private Long id;
    private String customerName;
    private String emailAddress;
    private String phoneNumber;
    private String uniqueId;
    private Product product;
    private int orderQuantity;
    private BigDecimal totalAmount;
    private String dateOrdered;
    private PaymentStatus paymentStatus;
}
