package com.tbless.inventoryManagementApp.services.order.helperClasses;

import com.tbless.inventoryManagementApp.dtos.response.AddCardDetailsResponse;

import static com.tbless.inventoryManagementApp.utils.ResponseUtils.CARD_ADDITION_SUCCESSFUL;
import static com.tbless.inventoryManagementApp.utils.ResponseUtils.CARD_ADDITION_UNSUCCESSFUL;

public class BuildResponse {
//    public static AddChildResponse buildAddChildResponse(Long parentId) {
//        AddChildResponse addChildResponse = new AddChildResponse();
//        addChildResponse.setMessage(CHILD_ADDITION_SUCCESSFUL);
//        addChildResponse.setId(parentId);
//        return addChildResponse;
//    }

//    public static AddChildResponse buildFailedToAddChildResponse(String parentEmailAddress) {
//        AddChildResponse addChildResponse = new AddChildResponse();
//        addChildResponse.setMessage(CHILD_ADDITION_UNSUCCESSFUL);
//        addChildResponse.setEmailAddress(parentEmailAddress);
//
//        return addChildResponse;
//    }

    public static AddCardDetailsResponse buildAddCardResponse(Long parentId) {
        AddCardDetailsResponse addCardDetailsResponse = new  AddCardDetailsResponse();
        addCardDetailsResponse.setMessage(CARD_ADDITION_SUCCESSFUL);
        addCardDetailsResponse.setId(parentId);

        return addCardDetailsResponse;
    }
    public static AddCardDetailsResponse buildFailedToAddCardResponse(String parentEmailAddress) {
        AddCardDetailsResponse addCardDetailsResponse = new AddCardDetailsResponse();
        addCardDetailsResponse.setMessage(CARD_ADDITION_UNSUCCESSFUL);
        addCardDetailsResponse.setEmailAddress(parentEmailAddress);

        return addCardDetailsResponse;
    }
}
