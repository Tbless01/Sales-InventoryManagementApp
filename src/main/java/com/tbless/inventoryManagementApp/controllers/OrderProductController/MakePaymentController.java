package com.tbless.inventoryManagementApp.controllers.OrderProductController;

import com.tbless.inventoryManagementApp.dtos.request.MakePaymentRequest;
import com.tbless.inventoryManagementApp.dtos.response.MakePaymentResponse;
import com.tbless.inventoryManagementApp.services.debitCard.DebitCardService;
import com.tbless.inventoryManagementApp.services.order.OrderService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order/")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class MakePaymentController {

    private final OrderService orderService;
    private final DebitCardService debitCardService;

    @SneakyThrows
    @PostMapping("makePayment/{uniqueId}")
    public ResponseEntity<MakePaymentResponse> makePayment(@PathVariable String uniqueId, @RequestBody MakePaymentRequest makePaymentRequest) {
        MakePaymentResponse response = orderService.makePayment(uniqueId, makePaymentRequest);
        return ResponseEntity.ok(response);
    }
//                MakePaymentResponse response = orderService.makePayment(uniqueId, makePaymentRequest);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}
//
//package com.tbless.inventoryManagementApp.controllers.OrderProductController;
//
//import com.tbless.inventoryManagementApp.dtos.request.MakePaymentRequest;
//import com.tbless.inventoryManagementApp.dtos.response.MakePaymentResponse;
//import com.tbless.inventoryManagementApp.services.debitCard.DebitCardService;
//import com.tbless.inventoryManagementApp.services.order.OrderService;
//import lombok.AllArgsConstructor;
//import lombok.SneakyThrows;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("api/v1/order/")
//@AllArgsConstructor
//@CrossOrigin(origins = "*")
//public class MakePaymentController {
//
//    private final OrderService orderService;
//    private final DebitCardService debitCardService;
//
//    @SneakyThrows
//    @PostMapping("makePayment")
//    public ResponseEntity<MakePaymentResponse> makePayment(
//            @RequestParam String emailAddress,
//            @RequestBody MakePaymentRequest makePaymentRequest
//    ) {
//        MakePaymentResponse response = orderService.makePayment(emailAddress, makePaymentRequest);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//}
