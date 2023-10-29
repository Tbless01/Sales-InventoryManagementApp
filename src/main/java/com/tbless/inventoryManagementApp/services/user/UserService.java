package com.tbless.inventoryManagementApp.services.user;

import com.tbless.inventoryManagementApp.data.models.User;
import com.tbless.inventoryManagementApp.dtos.response.UserResponse;
import com.tbless.inventoryManagementApp.exceptions.UserNotFoundException;
import com.tbless.inventoryManagementApp.exceptions.UserRegistrationException;
import com.tbless.inventoryManagementApp.utils.ApiResponse;

import java.util.List;

public interface UserService {

    User save(User user);
    User findUserByEmailAddress(String emailAddress);
    List<UserResponse> getAllUsers();
    void deleteAll();
}
