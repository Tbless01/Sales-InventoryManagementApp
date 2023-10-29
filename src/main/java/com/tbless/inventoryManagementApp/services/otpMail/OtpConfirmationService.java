package com.tbless.inventoryManagementApp.services.otpMail;


import com.tbless.inventoryManagementApp.utils.ApiResponse;

public interface OtpConfirmationService {
    ApiResponse confirmOtp(String otp);
}
