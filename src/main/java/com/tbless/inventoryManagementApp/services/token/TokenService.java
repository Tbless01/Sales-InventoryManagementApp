package com.tbless.inventoryManagementApp.services.token;

import com.tbless.inventoryManagementApp.data.models.Token;

import java.util.Optional;

public interface TokenService {
    Token saveToken(Token token);

    Optional<Token> findTokenByUserEmailAddress(String emailAddress);

    Optional<Token> findTokenByJwt(String jwt);
    void deleteExpiredAndRevokedTokens();

}
