package com.tbless.inventoryManagementApp.controllers.UserSearch;

import com.tbless.inventoryManagementApp.data.models.User;
import com.tbless.inventoryManagementApp.services.user.UserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @SneakyThrows
    @GetMapping("/getUserDetails/{emailAddress}")
    public User findUserByEmailAddress(@PathVariable String emailAddress) {
        return userService.findUserByEmailAddress(emailAddress);
    }
}
