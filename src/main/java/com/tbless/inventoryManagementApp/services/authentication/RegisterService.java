package com.tbless.inventoryManagementApp.services.authentication;

import com.tbless.inventoryManagementApp.data.models.Roles;
import com.tbless.inventoryManagementApp.data.models.User;
import com.tbless.inventoryManagementApp.dtos.request.RegistrationRequest;
import com.tbless.inventoryManagementApp.exceptions.UserAlreadyExistsException;
import com.tbless.inventoryManagementApp.exceptions.UserRegistrationException;
import com.tbless.inventoryManagementApp.security.JwtService;
import com.tbless.inventoryManagementApp.services.user.UserService;
import com.tbless.inventoryManagementApp.utils.ApiResponse;
import com.tbless.inventoryManagementApp.utils.GenerateApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.tbless.inventoryManagementApp.utils.ExceptionUtils.USER_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor

public class RegisterService {

    private final UserService userService;
    private final JwtService jwtService;
    public final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

        public ApiResponse register(RegistrationRequest registrationRequest) throws UserRegistrationException {
        boolean isRegisteredUser = userService.findUserByEmailAddress(registrationRequest.getEmailAddress()) != null;
        if (isRegisteredUser) {throw new UserRegistrationException("User already exists");}
        else {
            User savedUser = buildUser(registrationRequest);
            System.out.println(savedUser.toString());
            UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmailAddress());
            String jwt = jwtService.generateToken(userDetails);
            return GenerateApiResponse.createdResponse("Bearer " + jwt);
        }
    }

    private User buildUser(RegistrationRequest registrationRequest) {
        Set<Roles> userRoles = new HashSet<>();
        userRoles.add(Roles.USER);
        User user = User.builder()
                .emailAddress(registrationRequest.getEmailAddress())
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .genderType(registrationRequest.getGenderType())
                .userRoles(userRoles)
                .build();
        return userService.save(user);
    }

    private User checkIfUserAlreadyExist(RegistrationRequest userRegistrationRequest) throws UserAlreadyExistsException {
        if (userExist(userRegistrationRequest.getEmailAddress()))
            throw new UserAlreadyExistsException(String.format(USER_ALREADY_EXISTS, userRegistrationRequest.getEmailAddress()));
        User user = new User();
        return user;
    }

    private boolean userExist(String emailAddress) {
        User foundUser = userService.findUserByEmailAddress(emailAddress);
        return foundUser != null;
    }
}
