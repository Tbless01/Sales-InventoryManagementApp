package com.tbless.inventoryManagementApp.dtos.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserPasswordRequest {

    @NotEmpty(message = "Id could not be empty or null.")
    @Size(min = 1, max = 36, message = "Id must contains exactly out of 36 characters.")
    @Email
    private String email;
    private String oldPassword;
    private String newPassword;
}
