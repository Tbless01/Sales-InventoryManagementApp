package com.tbless.inventoryManagementApp.services.verification;

import com.tbless.inventoryManagementApp.data.models.VerificationToken;
import com.tbless.inventoryManagementApp.data.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenVerificationServiceImpl implements TokenVerificationService {
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public VerificationToken save(VerificationToken token) {
        return verificationTokenRepository.save(token);
    }

    @Override
    public Optional<VerificationToken> findVerificationTokenByToken(String token) {
        return verificationTokenRepository.findVerificationTokenByToken(token);
    }

    @Override
    public String generateVerificationToken(int length) {
       byte [] bytes = new byte[length];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }
}

