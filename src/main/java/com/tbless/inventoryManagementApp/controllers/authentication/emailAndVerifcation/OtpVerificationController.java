package com.tbless.inventoryManagementApp.controllers.authentication.emailAndVerifcation;


import com.tbless.inventoryManagementApp.services.otpMail.OtpConfirmationService;
import com.tbless.inventoryManagementApp.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/verifyEmail/sendOtp/")
@CrossOrigin(origins = "*")
public class OtpVerificationController {

    private final OtpConfirmationService otpConfirmationService;

    @PostMapping("confirmOtp")
    public ResponseEntity<ApiResponse> confirmOtp(@RequestParam String otp){
        return new ResponseEntity<>(otpConfirmationService.confirmOtp(otp), HttpStatus.OK);
    }
}
