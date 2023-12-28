package com.tbless.inventoryManagementApp.controllers.authentication;


import com.tbless.inventoryManagementApp.dtos.request.LoginRequest;
import com.tbless.inventoryManagementApp.services.authentication.LoginService;
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
public class LoginController {
    private final LoginService loginService;

    @SneakyThrows
    @PostMapping("login")
    @ResponseBody
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(loginService.login(loginRequest), HttpStatus.OK);
    }
}
