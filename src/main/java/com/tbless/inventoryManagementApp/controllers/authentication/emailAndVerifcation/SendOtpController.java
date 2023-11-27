package com.tbless.inventoryManagementApp.controllers.authentication.emailAndVerifcation;


import com.tbless.inventoryManagementApp.services.otpMail.SendOtpService;
import com.tbless.inventoryManagementApp.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/verifyEmail/")
@CrossOrigin(origins = "*")
public class SendOtpController {

    private final SendOtpService sendOtpService;

    @PostMapping ("sendOtp")
    public ResponseEntity<ApiResponse> sendOtp(@RequestParam String emailAddress){
        return new ResponseEntity<>(sendOtpService.sendOtp(emailAddress), HttpStatus.OK);
    }
}
