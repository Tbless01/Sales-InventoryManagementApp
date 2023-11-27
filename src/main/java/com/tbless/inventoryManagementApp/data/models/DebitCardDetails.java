package com.tbless.inventoryManagementApp.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DebitCardDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String debitCardNumber;
    private String cardHolderName;
    private String  expiringMonth;
    private String expiringYear;
    private String cvv;
    private String emailAddress;
}

