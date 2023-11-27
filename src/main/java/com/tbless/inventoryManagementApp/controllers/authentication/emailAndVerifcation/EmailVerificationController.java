package com.tbless.inventoryManagementApp.controllers.authentication.emailAndVerifcation;


import com.tbless.inventoryManagementApp.services.emailverification.EmailVerificationService;
import com.tbless.inventoryManagementApp.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("verifyEmail")
    public ResponseEntity<ApiResponse> verifyEmailAddress(@RequestParam String emailAddress){
        return new ResponseEntity<>(emailVerificationService.verifyEmailAddress(emailAddress), HttpStatus.OK);
    }
}
