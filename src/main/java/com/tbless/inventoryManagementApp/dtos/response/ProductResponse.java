package com.tbless.inventoryManagementApp.dtos.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    private List<String> imageUrl;
    private BigDecimal price;
    private int stock;
    private String description;
}

