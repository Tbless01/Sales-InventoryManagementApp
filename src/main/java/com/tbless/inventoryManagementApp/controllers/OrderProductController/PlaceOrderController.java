package com.tbless.inventoryManagementApp.controllers.OrderProductController;

import com.tbless.inventoryManagementApp.dtos.request.OrderRequest;
import com.tbless.inventoryManagementApp.dtos.response.OrderResponse;
import com.tbless.inventoryManagementApp.exceptions.InsufficientStockException;
import com.tbless.inventoryManagementApp.exceptions.ProductNotFoundException;
import com.tbless.inventoryManagementApp.services.order.OrderService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
@AllArgsConstructor
@CrossOrigin("*")
public class PlaceOrderController {
    private final OrderService orderService;

    @SneakyThrows
    @PostMapping("/placeOrder/{emailAddress}")
    public ResponseEntity<OrderResponse> placeOrder(@PathVariable String emailAddress,@RequestBody OrderRequest orderRequest) {
        try {
            OrderResponse response = orderService.placeOrder(emailAddress, orderRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ProductNotFoundException | InsufficientStockException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
