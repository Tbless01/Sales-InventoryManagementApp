package com.tbless.inventoryManagementApp.dtos.request;

import com.tbless.inventoryManagementApp.data.models.GenderType;
import com.tbless.inventoryManagementApp.data.models.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    private Long id;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String password;
//    private Set<Roles> userRole;
    private String userRole;
    private String phoneNumber;
    private String genderType;
}
