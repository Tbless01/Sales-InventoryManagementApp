package com.tbless.inventoryManagementApp.dtos.response;

import com.tbless.inventoryManagementApp.data.models.GenderType;
import com.tbless.inventoryManagementApp.data.models.Roles;
import jakarta.persistence.*;
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
        private String genderType;
}
