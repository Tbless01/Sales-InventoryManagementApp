package com.tbless.inventoryManagementApp.controllers.authentication;

import com.tbless.inventoryManagementApp.dtos.request.RegistrationRequest;
import com.tbless.inventoryManagementApp.exceptions.BadNetworkException;
import com.tbless.inventoryManagementApp.exceptions.UserAlreadyExistsException;
import com.tbless.inventoryManagementApp.exceptions.UserRegistrationException;
import com.tbless.inventoryManagementApp.services.authentication.RegisterService;
import com.tbless.inventoryManagementApp.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("api/v1/register")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    private final String loginRedirectLink = "http://localhost:3000/Login";
    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        ApiResponse response;
        try {
            response = registerService.register(registrationRequest);

            if (response.isSuccessful()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.errorResponse("User registration failed: " + response));
            }

        } catch (BadNetworkException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(ApiResponse.errorResponse("Bad network conditions. Please try again."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.errorResponse("Registration failed. Please check your internet connection and try again."));
        }
    }

    @GetMapping("/verify")
    public ModelAndView verifyUser(@RequestParam String token) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            boolean verificationResult = registerService.verifyUser(token);
            if (verificationResult) {
                RedirectView redirectView = new RedirectView(loginRedirectLink);
                modelAndView.setView(redirectView);
            } else {
                modelAndView.setViewName("verification-failed");
                modelAndView.addObject("errorMessage", "User verification failed. The token may be invalid or expired.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.setViewName("verification-failed");
            modelAndView.addObject("errorMessage", "User verification failed. Please try again.");
        }
        return modelAndView;
    }
}
