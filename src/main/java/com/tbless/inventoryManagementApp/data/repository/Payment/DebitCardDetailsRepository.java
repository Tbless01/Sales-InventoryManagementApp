package com.tbless.inventoryManagementApp.data.repository.Payment;

import com.tbless.inventoryManagementApp.data.models.DebitCardDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebitCardDetailsRepository extends JpaRepository<DebitCardDetails, Long> {

    DebitCardDetails findByDebitCardNumber(String debitCardNumber);
    DebitCardDetails findDebitCardDetailsByEmailAddress(String emailAddress);

    DebitCardDetails findByEmailAddress(String emailAddress);
}
