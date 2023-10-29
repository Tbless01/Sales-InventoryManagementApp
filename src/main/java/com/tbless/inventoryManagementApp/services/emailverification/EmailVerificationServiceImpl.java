package com.tbless.inventoryManagementApp.services.emailverification;

import com.tbless.inventoryManagementApp.services.user.UserService;
import com.tbless.inventoryManagementApp.utils.ApiResponse;
import com.tbless.inventoryManagementApp.utils.GenerateApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {
    private final UserService userService;

    @Override
    public ApiResponse verifyEmailAddress(String emailAddress) {

    var isNotRegistered = userService.findUserByEmailAddress(emailAddress)==null;
    if(isNotRegistered){
        return ApiResponse.builder()
                .isSuccessful(true)
                .httpStatus(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build();
    }
        return ApiResponse.builder()
                .isSuccessful(false)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .data(GenerateApiResponse.EMAIL_ALREADY_EXISTS)
                .build();
    }
}
