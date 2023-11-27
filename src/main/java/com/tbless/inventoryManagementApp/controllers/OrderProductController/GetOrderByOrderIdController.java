package com.tbless.inventoryManagementApp.controllers.OrderProductController;


import com.tbless.inventoryManagementApp.dtos.response.OrderResponse;
import com.tbless.inventoryManagementApp.services.order.OrderService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/api/v1/orders")
//@CrossOrigin("*")
//@AllArgsConstructor
//public class GetOrderByOrderIdController {
//
//    private final OrderService orderService;
//
//    @GetMapping("/{id}")
//    @SneakyThrows
//    public OrderResponse getOrderByOrderId(@PathVariable Long id) {
//        OrderResponse order = orderService.getOrderById(id);
//        return new ResponseEntity<>(order, HttpStatus.OK).getBody();
//    }
//}

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin("*")
@AllArgsConstructor
public class GetOrderByOrderIdController {

    private final OrderService orderService;

    @GetMapping("/place/{id}")
    @SneakyThrows
    public ResponseEntity<OrderResponse> getOrderByOrderId(@PathVariable Long id) {
        OrderResponse order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
