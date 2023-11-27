package com.tbless.inventoryManagementApp.controllers.OrderProductController;


import com.tbless.inventoryManagementApp.dtos.response.OrderResponse;
import com.tbless.inventoryManagementApp.services.order.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ListOfOrder {

    private final OrderService orderService;

    @GetMapping("/owner/{emailAddress}")
    public List<OrderResponse> getAllProductsOrderedByProductOwnerEmailAddress(@PathVariable String emailAddress) {
        return orderService.getAllProductsOrderedByProductOwnerEmailAddress(emailAddress);
    }

    @GetMapping("/customer/{emailAddress}")
    public List<OrderResponse> getAllProductsOrderedByCustomerEmailAddress(@PathVariable String emailAddress) {
        return orderService.getAllProductsOrderedByCustomerEmailAddress(emailAddress);
    }
}
