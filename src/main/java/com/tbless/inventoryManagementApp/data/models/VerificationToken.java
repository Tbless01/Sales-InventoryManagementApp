package com.tbless.inventoryManagementApp.data.models;


import com.tbless.inventoryManagementApp.data.models.enums.TakenStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "verificationTokens")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;

    private TakenStatus takenStatus;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    public VerificationToken(String token, LocalDateTime plusMinutes, LocalDateTime Now){
        this.token = token;
        this.createdAt = Now;
        this.expiredAt = plusMinutes;
    }
}

