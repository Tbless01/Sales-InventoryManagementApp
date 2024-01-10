package com.tbless.inventoryManagementApp.services.otpMail;


import com.tbless.inventoryManagementApp.data.models.enums.TakenStatus;
import com.tbless.inventoryManagementApp.data.models.VerificationToken;
import com.tbless.inventoryManagementApp.services.emailverification.EmailVerificationService;
import com.tbless.inventoryManagementApp.services.verification.TokenVerificationService;
import com.tbless.inventoryManagementApp.utils.ApiResponse;
import com.tbless.inventoryManagementApp.utils.GenerateApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class sendOtpServiceImpl implements SendOtpService {
    private final TokenVerificationService tokenVerificationService;
    private final JavaMailSender mailSender;
    private final EmailVerificationService emailVerificationService;

    //    @Value("${spring.mail.username}")
//    private  String EMAIL_SENDER;
    @Override
    public ApiResponse sendOtp(String emailAddress) {

        ApiResponse response = emailVerificationService.verifyEmailAddress(emailAddress);
        if (response.isSuccessful()) {
            String generatedOtp = tokenVerificationService.generateVerificationToken(3);
            VerificationToken verificationToken = VerificationToken.builder()
                    .token(generatedOtp)
                    .createdAt(LocalDateTime.now())
                    .expiredAt(LocalDateTime.now().plusMinutes(15L))
                    .takenStatus(TakenStatus.NOT_TAKEN)
                    .build();

            tokenVerificationService.save(verificationToken);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("ayomitobi1@gmail.com");
            simpleMailMessage.setTo(emailAddress);
            simpleMailMessage.setSubject(GenerateApiResponse.VERIFY_YOUR_ITS_YOU);
            simpleMailMessage.setText(verificationToken.getToken());
            System.out.println("I got here actually");
            mailSender.send(simpleMailMessage);

            return ApiResponse.builder()
                    .isSuccessful(true)
                    .build();
        }
         return ApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .isSuccessful(false)
                .build();
        }
    }
