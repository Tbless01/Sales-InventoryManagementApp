package com.tbless.inventoryManagementApp.dtos.response;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteResponse {
    private Long id;
    private String message;
}
