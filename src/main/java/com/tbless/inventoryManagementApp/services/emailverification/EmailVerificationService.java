package com.tbless.inventoryManagementApp.services.emailverification;

import com.tbless.inventoryManagementApp.utils.ApiResponse;

public interface EmailVerificationService {
    ApiResponse verifyEmailAddress(String emailAddress);
}
