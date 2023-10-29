package com.tbless.inventoryManagementApp.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductCreatedResponse {
    private Long id;
    private String message;
}
