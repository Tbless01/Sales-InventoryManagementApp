package com.tbless.inventoryManagementApp.services.EmailContact;

import com.tbless.inventoryManagementApp.dtos.request.ContactMailRequest;
import com.tbless.inventoryManagementApp.dtos.response.ContactMailResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContactMailServiceImpl implements ContactMailService {
    private final JavaMailSender javaMailSender;

    @Override
    public ContactMailResponse sendMessageForComplaint(ContactMailRequest contactMailRequest) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setFrom(contactMailRequest.getFromEmail());
            helper.setTo(contactMailRequest.getToEmail());
            helper.setSubject(contactMailRequest.getSubject());
            helper.setText(contactMailRequest.getBody());
            javaMailSender.send(mimeMessage);
            return new ContactMailResponse("Message successfully sent");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new ContactMailResponse("Failed to send message");
    }
}
