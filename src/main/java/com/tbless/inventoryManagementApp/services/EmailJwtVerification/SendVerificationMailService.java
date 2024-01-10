package com.tbless.inventoryManagementApp.services.EmailJwtVerification;

public interface SendVerificationMailService {
    void sendVerificationEmail(String toEmail, String subject, String body);

}
