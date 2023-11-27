package com.tbless.inventoryManagementApp.controllers.authentication;


import com.tbless.inventoryManagementApp.dtos.request.RegistrationRequest;
import com.tbless.inventoryManagementApp.services.authentication.RegisterService;
import com.tbless.inventoryManagementApp.utils.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/auth/")
@CrossOrigin(origins = "*")

public class RegisterController {
    private final RegisterService registerService;

    @SneakyThrows
    @PostMapping("register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        return new ResponseEntity<>(registerService.register(registrationRequest), HttpStatus.CREATED);
    }
}
