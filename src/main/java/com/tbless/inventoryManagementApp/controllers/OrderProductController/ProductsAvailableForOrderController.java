package com.tbless.inventoryManagementApp.controllers.OrderProductController;

import com.tbless.inventoryManagementApp.dtos.response.ProductResponse;
import com.tbless.inventoryManagementApp.services.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/product/")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ProductsAvailableForOrderController {
    private final ProductService productService;

    @GetMapping("/availableForOrder")
    public ResponseEntity<List<ProductResponse>> getAllAvailableProducts() {
        return new ResponseEntity<>(productService.getAllAvailableProducts(), HttpStatus.OK);
    }

    @GetMapping("/availableExceptOwnersProduct/{emailAddress}")
    public ResponseEntity<List<ProductResponse>> getAllAvailableProductsExceptOwnerProduct(@PathVariable String emailAddress) {
        return new  ResponseEntity<>(productService.getAllAvailableProductsExceptOwnerProduct(emailAddress), HttpStatus.OK);
    }
    @GetMapping("/searchAvailableExceptOwnersProduct/{emailAddress}/{keyword}")
    public ResponseEntity<List<ProductResponse>> searchAllAvailableProductsExceptOwnerProduct(@PathVariable String emailAddress, @PathVariable String keyword) {
        return new  ResponseEntity<>(productService.searchAllAvailableProductsExceptOwnerProduct(emailAddress, keyword), HttpStatus.OK);
    }
}
