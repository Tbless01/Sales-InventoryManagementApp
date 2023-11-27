package com.tbless.inventoryManagementApp.services.debitCard;

import com.tbless.inventoryManagementApp.data.models.DebitCardDetails;

public interface DebitCardService {

    DebitCardDetails save(DebitCardDetails debitCardDetails);
    DebitCardDetails findDebitCard (DebitCardDetails debitCardDetails);

    DebitCardDetails findByUserEmailAddress(String emailAddress);
}
