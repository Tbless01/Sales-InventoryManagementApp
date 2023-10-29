package com.tbless.inventoryManagementApp.utils;

import org.springframework.http.HttpStatus;

public class GenerateApiResponse {

    public static final String INVALID_CREDENTIALS = "You entered invalid credentials, please try again" ;
    public static final String BEARER ="Bearer ";
    public static final String VERIFY_YOUR_ITS_YOU = "Verify Otp code";
    public static final String VERIFY_OTP_TEXT = String.format("Kindly enter the the otp code below to verify your account %n %s%n Thank you ", new Object());
    public static final String EMAIL_ALREADY_EXISTS = "Email already Taken, Kindly try another";
    public static final String VERIFICATION_SUCCESSFUL = "Your email has been successfully verified";
    public static final String ALREADY_ASSIGNED_A_CARETAKER = "A caretaker has already been assigned to you" ;
    public static final String SUCCESSFULLY_ASSIGNED_A_CARETAKER = "Successful, Kindly proceed to make payment" ;
    public static final String CLOCKED_OUT_SUCCESSFUL = "You have successfully clocked out";
    public static final String NO_RECORD_FOUND = "We could not find your clock record history";
    public static final String CLOCKED_IN_SUCCESSFULLY = "You have clocked in successfully";
    public static final String CHILD_ADDED_SUCCESSFULLY = "You have successfully added a child";
    public static final Object UNABLE_TO_ADD_CHILD = "Unable to add child";
    public static final String NO_AVAILABLE_CARETAKER = "No Available Care taker at the moment";
    public static final String UPDATE_FAILED = "Unable to update profile";
    public static final String PROFILE_UPDATED_SUCCESSFULLY = "Profile updated successfully";
    public static final String CARETAKER_NOT_AVAILABLE = "CareTaker Is Not Available";
    public static final String SUCCESSFUL_KINDLY_MAKE_PAYMENT = "Successful, Kindly proceed to make payment";

    public static ApiResponse createdResponse(Object data){
        return ApiResponse.builder()
                .httpStatus(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .isSuccessful(true)
                .data(data)
                .build();
    }

    public static ApiResponse okResponse(Object data) {
        return ApiResponse.builder()
                .data(data)
                .httpStatus(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
    }

    public static ApiResponse sendClockRecordData(String clockId) {
        return ApiResponse.builder()
                .data(clockId)
                .httpStatus(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
    }

    public static ApiResponse BadResponse(Object data) {
        return ApiResponse.builder()
                .data(data)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .isSuccessful(false)
                .build();
    }

    public static ApiResponse okClockResponse(Object data) {
        return ApiResponse.builder()
                .data(data)
                .httpStatus(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .isSuccessful(true)
                .build();
    }

    public static ApiResponse notOkResponse(Object data) {
        return ApiResponse.builder()
                .data(data)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .isSuccessful(false)
                .build();
    }
}
