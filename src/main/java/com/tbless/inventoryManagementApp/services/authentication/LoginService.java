package com.tbless.inventoryManagementApp.services.authentication;


import com.tbless.inventoryManagementApp.data.models.Token;
import com.tbless.inventoryManagementApp.dtos.request.LoginRequest;
import com.tbless.inventoryManagementApp.exceptions.UserLoginException;
import com.tbless.inventoryManagementApp.security.JwtService;
import com.tbless.inventoryManagementApp.services.token.TokenService;
import com.tbless.inventoryManagementApp.utils.ApiResponse;
import com.tbless.inventoryManagementApp.utils.GenerateApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final TokenService tokenService;

    public ApiResponse login(LoginRequest loginRequest) throws UserLoginException {
        authenticateUser(loginRequest);
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmailAddress());
        if(userDetails==null) throw new UserLoginException(GenerateApiResponse.INVALID_CREDENTIALS);
        String jwt = jwtService.generateToken(userDetails);
        revokeAllUserToken(loginRequest.getEmailAddress());
        saveToken(jwt, loginRequest.getEmailAddress());
        return GenerateApiResponse.okResponse(GenerateApiResponse.BEARER +jwt);
    }

    private void saveToken(String jwt, String emailAddress) {
        Token token = Token.builder()
                .jwt(jwt)
                .isExpired(false)
                .isRevoked(false)
                .userEmailAddress(emailAddress)
                .build();
        tokenService.saveToken(token);
    }

    private void revokeAllUserToken(String emailAddress) {
        var allUsertoken = tokenService.findTokenByUserEmailAddress(emailAddress);
        if(allUsertoken.isEmpty()) return;
        allUsertoken.
                ifPresent(token -> {
                    token.setRevoked(true);
                    token.setExpired(true);
                    tokenService.saveToken(token);
                });
    }

    private void authenticateUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmailAddress(), loginRequest.getPassword()));
    }
}
