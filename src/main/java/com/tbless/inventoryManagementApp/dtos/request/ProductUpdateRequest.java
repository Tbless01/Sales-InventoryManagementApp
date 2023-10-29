package com.tbless.inventoryManagementApp.dtos.request;

import lombok.*;

import java.math.BigDecimal;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductUpdateRequest {
    private String name;
    private BigDecimal price;
    private int stock;
    private String description;
}
