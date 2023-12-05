//package com.tbless.inventoryManagementApp.services.emailverification;
//
//import com.tbless.inventoryManagementApp.dtos.request.RegistrationRequest;
//import com.tbless.inventoryManagementApp.exceptions.BadNetworkException;
//import com.tbless.inventoryManagementApp.exceptions.UserRegistrationException;
//import com.tbless.inventoryManagementApp.services.authentication.RegisterService;
//import com.tbless.inventoryManagementApp.services.user.UserService;
//import com.tbless.inventoryManagementApp.utils.ApiResponse;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//class EmailVerificationServiceImplTest {
//
//    @Autowired
//    private EmailVerificationService emailVerificationService;
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private RegisterService registerService;
//
//    @Test
//    void testThatEmailCanBeVerified() throws UserRegistrationException, BadNetworkException {
//        RegistrationRequest registrationRequest = new RegistrationRequest();
//        registrationRequest.setFirstName("PAUL");
//        registrationRequest.setGenderType("MALE");
//        registrationRequest.setEmailAddress("Tayo@gmail.com");
//        registrationRequest.setPassword("password");
//
//        var registrationResponse = registerService.register(registrationRequest);
//
//        ApiResponse response = emailVerificationService.verifyEmailAddress("dami@gmail.com");
//
//        ApiResponse emailVerification = emailVerificationService.verifyEmailAddress("Tayo@gmail.com");
//        assertThat(registrationResponse.isSuccessful()).isTrue();
//        assertThat(response.isSuccessful()).isTrue();
//        assertThat(emailVerification.isSuccessful()).isFalse();
//    }
//}
