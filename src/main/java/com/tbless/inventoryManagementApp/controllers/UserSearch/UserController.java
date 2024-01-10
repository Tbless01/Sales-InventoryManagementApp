package com.tbless.inventoryManagementApp.controllers.UserSearch;

import com.tbless.inventoryManagementApp.dtos.request.UserImageUpdateRequest;
import com.tbless.inventoryManagementApp.dtos.response.UserResponse;
import com.tbless.inventoryManagementApp.exceptions.UserNotFoundException;
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
public class UserController {
    private final UserService userService;

    @GetMapping("/getUserDetails/{emailAddress}")
    public ResponseEntity<UserResponse> findUserByEmailAddress(@PathVariable String emailAddress) {
        return new ResponseEntity<>(userService.findUserByEmailAddressProfile(emailAddress), HttpStatus.OK);
    }

    @PutMapping("/profilePixUpdate/{emailAddress}")
    public ResponseEntity<String> findUserByEmailAddress(@PathVariable String emailAddress, @RequestBody UserImageUpdateRequest userUpdateRequest) throws UserNotFoundException {
        return new ResponseEntity<>(userService.updateUserProfilePicture(emailAddress, userUpdateRequest), HttpStatus.OK);
    }
}
