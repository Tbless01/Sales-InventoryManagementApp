package com.tbless.inventoryManagementApp.services.debitCard;

import com.tbless.inventoryManagementApp.data.models.DebitCardDetails;
import com.tbless.inventoryManagementApp.data.repository.Payment.DebitCardDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class DebitCardServiceImpl implements DebitCardService{
    private final DebitCardDetailsRepository debitCardDetailsRepository;

    @Override
    public DebitCardDetails save(DebitCardDetails debitCardDetails) {
        return debitCardDetailsRepository.save(debitCardDetails);
    }

    @Override
    public DebitCardDetails findDebitCard(DebitCardDetails debitCardDetails) {
        return debitCardDetailsRepository.findByDebitCardNumber(debitCardDetails.getDebitCardNumber());
    }

    @Override
    public DebitCardDetails findByUserEmailAddress(String emailAddress) {
        return debitCardDetailsRepository.findByEmailAddress(emailAddress);
    }
}
