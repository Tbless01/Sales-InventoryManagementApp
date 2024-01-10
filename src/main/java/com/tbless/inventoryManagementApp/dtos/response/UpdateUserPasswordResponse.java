package com.tbless.inventoryManagementApp.dtos.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserPasswordResponse {
    private String email;
    private String newPassword;
}
