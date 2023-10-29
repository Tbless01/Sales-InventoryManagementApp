package com.tbless.inventoryManagementApp.data.repository;

import com.tbless.inventoryManagementApp.data.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findVerificationTokenByToken(String token);
}
