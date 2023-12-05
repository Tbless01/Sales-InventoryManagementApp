package com.tbless.inventoryManagementApp.services.registerUser;

import com.tbless.inventoryManagementApp.dtos.request.RegistrationRequest;
import com.tbless.inventoryManagementApp.dtos.response.UserResponse;
import com.tbless.inventoryManagementApp.exceptions.BadNetworkException;
import com.tbless.inventoryManagementApp.exceptions.UserRegistrationException;
import com.tbless.inventoryManagementApp.services.authentication.LoginService;
import com.tbless.inventoryManagementApp.services.authentication.RegisterService;
import com.tbless.inventoryManagementApp.services.user.UserService;
import com.tbless.inventoryManagementApp.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Slf4j
@SpringBootTest
public class RegisterServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private LoginService loginService;
    private ApiResponse apiResponse;


    @BeforeEach
    public void testRegister() throws UserRegistrationException, BadNetworkException {
        userService.deleteAll();
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setEmailAddress("test@example.com");
        registrationRequest.setFirstName("John");
        registrationRequest.setLastName("Doe");
        registrationRequest.setPassword("password");
        registrationRequest.setGenderType("MALE");
        apiResponse = registerService.register(registrationRequest);
    }

    @Test
    public void testThatUserCanRegister() {
        assertThat(apiResponse).isNotNull();
    }

    @Test
    public void getAllCustomersTest() throws UserRegistrationException, BadNetworkException {
        RegistrationRequest registrationRequest2 = new RegistrationRequest();
        registrationRequest2.setEmailAddress("test2@example.com");
        registrationRequest2.setFirstName("Doe");
        registrationRequest2.setLastName("John");
        registrationRequest2.setPassword("password");
        registrationRequest2.setGenderType("FEMALE");
        registerService.register(registrationRequest2);
        List<UserResponse> users = userService.getAllUsers();
        assertThat(users.size()).isEqualTo(2);
//        OR
//        assertThat(users.size()).isEqualTo(TWO.intValue());
    }
}