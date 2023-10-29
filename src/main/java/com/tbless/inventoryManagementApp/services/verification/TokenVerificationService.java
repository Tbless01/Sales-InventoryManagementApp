package com.tbless.inventoryManagementApp.services.verification;

import com.tbless.inventoryManagementApp.data.models.VerificationToken;

import java.util.Optional;

public interface TokenVerificationService {
    VerificationToken save(VerificationToken token);

    Optional<VerificationToken> findVerificationTokenByToken(String token);

    String generateVerificationToken(int length);

}
