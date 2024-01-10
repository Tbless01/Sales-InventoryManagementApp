package com.tbless.inventoryManagementApp.services.emailverification;

import com.tbless.inventoryManagementApp.dtos.request.RegistrationRequest;
import com.tbless.inventoryManagementApp.exceptions.UserRegistrationException;
import com.tbless.inventoryManagementApp.services.authentication.RegisterService;
import com.tbless.inventoryManagementApp.services.user.UserService;
import com.tbless.inventoryManagementApp.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
//@ActiveProfiles("test")
// OR
@TestPropertySource(locations = "classpath:application-test.properties")
class RegisterServiceImplTest {
    @Autowired
    private RegisterService registerService;
    @Autowired
    private UserService userService;

    @Test
    void testThatEmailCanBeVerified() throws UserRegistrationException {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName("PAUL");
        registrationRequest.setEmailAddress("Tayo@gmail.com");
        registrationRequest.setLastName("Godwin");
        registrationRequest.setPhoneNumber("07087605675");
        registrationRequest.setPassword("password");

        ApiResponse registrationResponse = registerService.register(registrationRequest);
        log.info("This please "+registrationResponse.toString());

        assertThat(registrationResponse.isSuccessful()).isTrue();
        assertEquals(1, userService.count());
    }
}
