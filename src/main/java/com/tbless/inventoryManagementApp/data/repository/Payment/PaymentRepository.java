package com.tbless.inventoryManagementApp.data.repository.Payment;

import com.tbless.inventoryManagementApp.data.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
