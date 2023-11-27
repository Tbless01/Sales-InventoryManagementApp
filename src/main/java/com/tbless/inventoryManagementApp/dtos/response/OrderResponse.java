package com.tbless.inventoryManagementApp.dtos.response;

import com.tbless.inventoryManagementApp.data.models.Product;
import lombok.*;

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
    private String date;
}
