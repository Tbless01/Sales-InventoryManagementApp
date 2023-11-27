package com.tbless.inventoryManagementApp.services.payment;

import com.tbless.inventoryManagementApp.data.models.Payment;
import com.tbless.inventoryManagementApp.data.repository.Payment.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;
    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }
}
