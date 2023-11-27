package com.tbless.inventoryManagementApp.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import static com.tbless.inventoryManagementApp.utils.ResponseUtils.FIELD_MUST_NOT_BE_EMPTY;

@Getter
@Setter

public class AddCardDetailsRequest {

    @NotNull(message = FIELD_MUST_NOT_BE_EMPTY)
    @NotEmpty(message = FIELD_MUST_NOT_BE_EMPTY)
    private String debitCardNumber;
    @NotNull(message = FIELD_MUST_NOT_BE_EMPTY)
    @NotEmpty(message = FIELD_MUST_NOT_BE_EMPTY)
    private String expiringMonth;
    @NotNull(message = FIELD_MUST_NOT_BE_EMPTY)
    @NotEmpty(message = FIELD_MUST_NOT_BE_EMPTY)
    private String  expiringYear;
    @NotNull(message = FIELD_MUST_NOT_BE_EMPTY)
    @NotEmpty(message = FIELD_MUST_NOT_BE_EMPTY)
    private String cvv;
    @NotNull(message = FIELD_MUST_NOT_BE_EMPTY)
    @NotEmpty(message = FIELD_MUST_NOT_BE_EMPTY)
    private  String cardHolderName;
    private String emailAddress;

}
