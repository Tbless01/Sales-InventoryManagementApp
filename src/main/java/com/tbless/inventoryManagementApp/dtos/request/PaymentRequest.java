package com.tbless.inventoryManagementApp.dtos.request;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private String emailAddress;
    private BigDecimal amount;
}
