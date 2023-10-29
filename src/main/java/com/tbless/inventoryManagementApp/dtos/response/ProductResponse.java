package com.tbless.inventoryManagementApp.dtos.response;

import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResponse {

    private String name;
    private BigDecimal price;
    private int stock;
    private String description;
}

