package com.tbless.inventoryManagementApp.services.otpMail;

import com.tbless.inventoryManagementApp.utils.ApiResponse;

public interface SendOtpService {
    ApiResponse sendOtp(String emailAddress);
}
