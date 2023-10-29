package com.tbless.inventoryManagementApp.data.repository;

import com.tbless.inventoryManagementApp.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailAddressIgnoreCase(String emailAddress);
}