package com.tbless.inventoryManagementApp.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContactMailRequest {
    private String fromEmail;
    private String toEmail;
    private String subject;
    private String body;
}
