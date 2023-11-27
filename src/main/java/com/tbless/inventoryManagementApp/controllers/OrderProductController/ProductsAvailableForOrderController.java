package com.tbless.inventoryManagementApp.controllers.OrderProductController;

import com.tbless.inventoryManagementApp.dtos.response.ProductResponse;
import com.tbless.inventoryManagementApp.services.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/product/")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ProductsAvailableForOrderController {
    private final ProductService productService;

    @GetMapping("/availableForOrder")
    public List<ProductResponse> getAllAvailableProducts() {
        return productService.getAllAvailableProducts();
    }

    @GetMapping("/availableExceptOwnersProduct/{emailAddress}")
    public List<ProductResponse> getAllAvailableProductsExceptOwnerProduct(@PathVariable String emailAddress) {
        return productService.getAllAvailableProductsExceptOwnerProduct(emailAddress);
    }
}
