package com.tbless.inventoryManagementApp.controllers.OrderProductController;

import com.tbless.inventoryManagementApp.services.debitCard.DebitCardService;
import com.tbless.inventoryManagementApp.services.order.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class MakePaymentController {

    private final OrderService orderService;

    //    @SneakyThrows
//    @PostMapping("makePayment/{uniqueId}")
//    public ResponseEntity<MakePaymentResponse> makePayment(@PathVariable String uniqueId, @RequestBody MakePaymentRequest makePaymentRequest) {
//        MakePaymentResponse response = orderService.makePayment(uniqueId, makePaymentRequest);
//        return ResponseEntity.ok(response);
//    }
    @PostMapping("/makePayment/{uniqueId}")
    public ResponseEntity<String> makePayment(@PathVariable String uniqueId) {
        try {
            orderService.makeOrderPayment(uniqueId);
            System.out.println("Payment successful "+ uniqueId);
            return ResponseEntity.ok("Payment successful for order with uniqueId: " + uniqueId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
