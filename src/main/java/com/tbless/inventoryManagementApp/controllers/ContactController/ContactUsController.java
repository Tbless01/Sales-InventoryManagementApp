package com.tbless.inventoryManagementApp.controllers.ContactController;

import com.tbless.inventoryManagementApp.dtos.request.ContactMailRequest;
import com.tbless.inventoryManagementApp.dtos.request.UserImageUpdateRequest;
import com.tbless.inventoryManagementApp.dtos.response.ContactMailResponse;
import com.tbless.inventoryManagementApp.dtos.response.UserResponse;
import com.tbless.inventoryManagementApp.exceptions.UserNotFoundException;
import com.tbless.inventoryManagementApp.services.EmailContact.ContactMailService;
import com.tbless.inventoryManagementApp.services.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
@AllArgsConstructor
public class ContactUsController {
    private final ContactMailService contactMailService;

    @PostMapping("/contact")
    public ResponseEntity<ContactMailResponse> contactMail(@RequestBody ContactMailRequest contactMailRequest) {
        return new ResponseEntity<>(contactMailService.sendMessageForComplaint(contactMailRequest),HttpStatus.OK);
    }
}