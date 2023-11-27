package com.tbless.inventoryManagementApp.controllers.productContoller;

import com.tbless.inventoryManagementApp.dtos.response.ProductResponse;
import com.tbless.inventoryManagementApp.services.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product/")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ProductListByEmailController {
    private final ProductService productService;

    @GetMapping("availableByEmail/{emailAddress}")
    public List<ProductResponse> getAllAvailableProductsByEmailAddress( @PathVariable String emailAddress) {
        return productService.getAllAvailableProductsByEmailAddress(emailAddress);
    }
}
