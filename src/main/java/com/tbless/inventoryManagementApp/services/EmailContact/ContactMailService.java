package com.tbless.inventoryManagementApp.services.EmailContact;

import com.tbless.inventoryManagementApp.dtos.request.ContactMailRequest;
import com.tbless.inventoryManagementApp.dtos.response.ContactMailResponse;

public interface ContactMailService {
    ContactMailResponse sendMessageForComplaint(ContactMailRequest contactMailRequest);
}
