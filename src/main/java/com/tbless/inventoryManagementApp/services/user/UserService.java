package com.tbless.inventoryManagementApp.services.user;

import com.tbless.inventoryManagementApp.data.models.User;
import com.tbless.inventoryManagementApp.dtos.request.UpdateUserPasswordRequest;
import com.tbless.inventoryManagementApp.dtos.request.UserImageUpdateRequest;
import com.tbless.inventoryManagementApp.dtos.response.UpdateUserPasswordResponse;
import com.tbless.inventoryManagementApp.dtos.response.UserResponse;
import com.tbless.inventoryManagementApp.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {

    User save(User user);
    User findUserByEmailAddress(String emailAddress);
    UserResponse findUserByEmailAddressProfile(String emailAddress);
    long count();
    List<UserResponse> getAllUsers();
    UpdateUserPasswordResponse updateUserAccount(UpdateUserPasswordRequest request);
    String updateUserProfilePicture(String emailAddress, UserImageUpdateRequest userUpdateRequest) throws UserNotFoundException;
    void deleteAll();
}
