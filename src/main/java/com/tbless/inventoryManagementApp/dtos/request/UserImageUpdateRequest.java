package com.tbless.inventoryManagementApp.dtos.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserImageUpdateRequest {
    private String imageUrl;
}
