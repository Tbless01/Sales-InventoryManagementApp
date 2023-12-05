package com.tbless.inventoryManagementApp.dtos.request;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductRequest {
    private String emailAddress;
    private String name;
    private List<String> imageUrl;
    private BigDecimal price;
    private int stock;
    private String description;
}
