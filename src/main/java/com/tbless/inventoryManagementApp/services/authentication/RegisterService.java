//package com.tbless.inventoryManagementApp.services.authentication;
//
//import com.tbless.inventoryManagementApp.data.models.Roles;
//import com.tbless.inventoryManagementApp.data.models.User;
//import com.tbless.inventoryManagementApp.dtos.request.RegistrationRequest;
//import com.tbless.inventoryManagementApp.exceptions.UserAlreadyExistsException;
//import com.tbless.inventoryManagementApp.exceptions.UserRegistrationException;
//import com.tbless.inventoryManagementApp.security.JwtService;
//import com.tbless.inventoryManagementApp.services.user.UserService;
//import com.tbless.inventoryManagementApp.utils.ApiResponse;
//import com.tbless.inventoryManagementApp.utils.GenerateApiResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import static com.tbless.inventoryManagementApp.utils.ExceptionUtils.USER_ALREADY_EXISTS;
//
//@Service
//@RequiredArgsConstructor
//
//public class RegisterService {
//
//    private final UserService userService;
//    private final JwtService jwtService;
//    public final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//
//        public ApiResponse register(RegistrationRequest registrationRequest) throws UserRegistrationException {
//        boolean isRegisteredUser = userService.findUserByEmailAddress(registrationRequest.getEmailAddress()) != null;
//        if (isRegisteredUser) {throw new UserRegistrationException("User already exists");}
//        else {
//            User savedUser = buildUser(registrationRequest);
//            System.out.println(savedUser.toString());
//            UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmailAddress());
//            String jwt = jwtService.generateToken(userDetails);
//            return GenerateApiResponse.createdResponse("Bearer " + jwt);
//        }
//    }
//
//    private User buildUser(RegistrationRequest registrationRequest) {
//        Set<Roles> userRoles = new HashSet<>();
//        userRoles.add(Roles.USER);
//        User user = User.builder()
//                .emailAddress(registrationRequest.getEmailAddress())
//                .firstName(registrationRequest.getFirstName())
//                .lastName(registrationRequest.getLastName())
//                .phoneNumber(registrationRequest.getPhoneNumber())
//                .password(passwordEncoder.encode(registrationRequest.getPassword()))
//                .genderType(registrationRequest.getGenderType())
//                .userRoles(userRoles)
//                .build();
//        return userService.save(user);
//    }
//
//    private User checkIfUserAlreadyExist(RegistrationRequest userRegistrationRequest) throws UserAlreadyExistsException {
//        if (userExist(userRegistrationRequest.getEmailAddress()))
//            throw new UserAlreadyExistsException(String.format(USER_ALREADY_EXISTS, userRegistrationRequest.getEmailAddress()));
//        User user = new User();
//        return user;
//    }
//
//    private boolean userExist(String emailAddress) {
//        User foundUser = userService.findUserByEmailAddress(emailAddress);
//        return foundUser != null;
//    }
//}


package com.tbless.inventoryManagementApp.services.authentication;

import com.tbless.inventoryManagementApp.data.models.Roles;
import com.tbless.inventoryManagementApp.data.models.Token;
import com.tbless.inventoryManagementApp.data.models.User;
import com.tbless.inventoryManagementApp.dtos.request.RegistrationRequest;
import com.tbless.inventoryManagementApp.exceptions.BadNetworkException;
import com.tbless.inventoryManagementApp.exceptions.UserLoginException;
import com.tbless.inventoryManagementApp.exceptions.UserRegistrationException;
import com.tbless.inventoryManagementApp.security.JwtService;
import com.tbless.inventoryManagementApp.services.EmailJwtVerification.SendVerificationMailService;
import com.tbless.inventoryManagementApp.services.token.TokenService;
import com.tbless.inventoryManagementApp.services.user.UserService;
import com.tbless.inventoryManagementApp.utils.ApiResponse;
import com.tbless.inventoryManagementApp.utils.GenerateApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserService userService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final SendVerificationMailService sendVerificationMailService;

    public ApiResponse register(RegistrationRequest registrationRequest) throws UserRegistrationException , BadNetworkException {
        if (!isInternetReachable()) {
            throw new BadNetworkException("Bad network conditions. Unable to register. Please check your internet connection and try again.");
        }
        boolean isRegisteredUser = userService.findUserByEmailAddress(registrationRequest.getEmailAddress()) != null;
        if (isRegisteredUser) {
            throw new UserRegistrationException("User already exists");
        } else {
            User user = buildUser(registrationRequest);
            user.setEnabled(false);
            userService.save(user);

            UserDetails userDetails = userDetailsService.loadUserByUsername(registrationRequest.getEmailAddress());
            String jwt = jwtService.generateToken(userDetails);
//            String verificationToken = generateVerificationToken(user);
            Token token = Token.builder()
                    .userEmailAddress(user.getEmailAddress())
                    .jwt(jwt)
                    .build();
            tokenService.saveToken(token);
            sendVerificationEmail(user.getEmailAddress(), jwt);
            return GenerateApiResponse.createdResponse("Registration successful. Please check your email for verification.");
        }
    }
    private static boolean isInternetReachable() {
        try {
            URL url = new URL("https://www.google.com"); // Replace with a reliable and reachable server
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            return false;
        }
    }


    public boolean verifyUser(String token) {
        Token verificationToken = tokenService.findTokenByJwt(token)
                .orElseThrow(() -> new RuntimeException("Verification token not found"));
        if (verificationToken.isExpired() || verificationToken.isRevoked()) {
            return false;
        }
        User user = userService.findUserByEmailAddress(verificationToken.getUserEmailAddress());
        if (user != null) {
            user.setEnabled(true);
            userService.save(user);
            verificationToken.setRevoked(true);
            tokenService.saveToken(verificationToken);
            return true;
        }
        return false;
    }

    private String generateVerificationToken(User user) {
        return UUID.randomUUID().toString();
    }

    private void sendVerificationEmail(String emailAddress, String verificationToken) {
        // Construct a nice email message with the verification link
        var user =userService.findUserByEmailAddress(emailAddress);
        String verificationLink = "http://localhost:8080/api/v1/register/verify?token=" + verificationToken;
        String loginRedirectLink = "http://localhost:3000/Login";
        String emailMessage = "<html>"
                + "<body>"
                + "<p>Dear " + user.getFirstName() + ",</p>"
                + "<p>Thank you for registering with our application!</p>"
                + "<p>To verify your email and activate your account, please click the link below:</p>"
                + "<a href='" + verificationLink + "'>Click here to verify</a>"
                + "<p>If you are not automatically redirected, you can use the following link to login:</p>"
                + "<a href='" + loginRedirectLink + "'> Redirect to login </a>"
                + "<p>Best regards,<br/>The StockSpan Team</p>"
                + "</body>"
                + "</html>";

        // Send the email using the custom email service
        sendVerificationMailService.sendVerificationEmail(emailAddress, "Account Verification - StockSpan", emailMessage);
    }

    private User buildUser(RegistrationRequest registrationRequest) {
        Set<Roles> userRoles = new HashSet<>();
        userRoles.add(Roles.USER);
        return User.builder()
                .emailAddress(registrationRequest.getEmailAddress())
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .genderType(registrationRequest.getGenderType())
                .userRoles(userRoles)
                .build();
    }
}
