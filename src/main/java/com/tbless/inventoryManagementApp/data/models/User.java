package com.tbless.inventoryManagementApp.data.models;

import com.tbless.inventoryManagementApp.data.models.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;


@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String emailAddress;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Roles> userRoles;
    private boolean isEnabled = false;
    private String imageUrl;
}
