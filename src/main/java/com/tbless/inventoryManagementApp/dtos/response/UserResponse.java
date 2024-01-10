package com.tbless.inventoryManagementApp.dtos.response;

import com.tbless.inventoryManagementApp.data.models.enums.Roles;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
        private Long id;
        private String emailAddress;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private Set<Roles> userRoles;
        private String imageUrl;
}
