package com.tbless.inventoryManagementApp.controllers.OrderProductController;

import com.tbless.inventoryManagementApp.dtos.request.AddCardDetailsRequest;
import com.tbless.inventoryManagementApp.dtos.response.AddCardDetailsResponse;
import com.tbless.inventoryManagementApp.services.debitCard.DebitCardService;
import com.tbless.inventoryManagementApp.services.order.OrderService;
import com.tbless.inventoryManagementApp.services.user.UserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order/")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class UserCardController {

    private final UserService userService;
    private final DebitCardService debitCardService;
    private final OrderService orderService;

    @SneakyThrows
    @PostMapping("addDebitCard/{emailAddress}")
    public ResponseEntity<AddCardDetailsResponse> addDebitCard(@PathVariable String emailAddress, @RequestBody AddCardDetailsRequest addCardDetailsRequest) {
        AddCardDetailsResponse response = orderService.addDebitCard(emailAddress, addCardDetailsRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
