//package com.tbless.inventoryManagementApp.controllers.DebitCardController;
//
//
//import com.tbless.inventoryManagementApp.data.models.DebitCardDetails;
//import com.tbless.inventoryManagementApp.dtos.request.AddCardDetailsRequest;
//import com.tbless.inventoryManagementApp.dtos.response.AddCardDetailsResponse;
//import com.tbless.inventoryManagementApp.services.order.OrderService;
//import lombok.AllArgsConstructor;
//import lombok.SneakyThrows;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/card/")
//@AllArgsConstructor
//@CrossOrigin("*")
//public class AddDebitCardController {
//
//    private final OrderService orderService;
//
//    @SneakyThrows
//    @PostMapping("addDebitCard/{emailAddress}")
//    public AddCardDetailsResponse addDebitCard(@PathVariable String emailAddress, @RequestBody AddCardDetailsRequest addCardDetailsRequest) {
//        return orderService.addDebitCard(emailAddress, addCardDetailsRequest);
//    }
//
//    @SneakyThrows
//    @GetMapping("getExistingCardDetails/{emailAddress}")
//    public DebitCardDetails getExistingCardDetails(@PathVariable String emailAddress) {
//        return orderService.getExistingCardByEmail(emailAddress);
//    }
//
//}
