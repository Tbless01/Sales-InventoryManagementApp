package com.tbless.inventoryManagementApp.data.repository;

import com.tbless.inventoryManagementApp.data.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository  extends JpaRepository<Token, Long> {

        Optional<Token> findTokenByUserEmailAddress(String userEmailAddress);

        Optional<Token> findTokenByJwt(String jwt);
}
