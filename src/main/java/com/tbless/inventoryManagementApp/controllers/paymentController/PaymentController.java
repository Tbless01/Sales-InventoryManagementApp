package com.tbless.inventoryManagementApp.controllers.paymentController;

import com.tbless.inventoryManagementApp.dtos.request.PaymentRequest;
import com.tbless.inventoryManagementApp.services.PaystackInt.PaystackService.PaystackService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin("*")
@AllArgsConstructor
public class PaymentController {
    private final PaystackService paystackService;

    @PostMapping("/initiate-payment")
    public ResponseEntity<String> initializeTransaction(@RequestBody PaymentRequest paymentRequest) {
        try {
            return ResponseEntity.ok(paystackService.initializeTransaction(paymentRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to initialize transaction: " + e.getMessage());
        }
    }

    @GetMapping("/verify-payment/{id}/{reference}")
    public ResponseEntity<String> verifyTransaction(@PathVariable Long id,@PathVariable String reference) {
        try {
            return ResponseEntity.ok(paystackService.verifyTransaction(id, reference));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to verify transaction: " + e.getMessage());
        }
    }
}