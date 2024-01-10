package com.tbless.inventoryManagementApp.services.PaystackInt.PaystackService;

import com.tbless.inventoryManagementApp.dtos.request.PaymentRequest;
import com.tbless.inventoryManagementApp.exceptions.OrderNotFoundException;
import com.tbless.inventoryManagementApp.exceptions.UserNotFoundException;

public interface PaystackService {
    String initializeTransaction(PaymentRequest paymentRequest) throws UserNotFoundException;
    String verifyTransaction(Long id, String reference) throws OrderNotFoundException;
}
