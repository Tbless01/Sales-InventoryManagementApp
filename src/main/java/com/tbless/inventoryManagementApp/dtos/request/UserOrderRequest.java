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
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderRequest {
    private String emailAddress;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    
    private Set<Roles> roles;
    private GenderType genderType;
}
