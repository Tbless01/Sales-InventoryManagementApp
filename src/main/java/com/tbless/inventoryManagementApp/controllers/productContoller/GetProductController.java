package com.tbless.inventoryManagementApp.controllers.productContoller;

import com.tbless.inventoryManagementApp.dtos.response.ProductResponse;
import com.tbless.inventoryManagementApp.exceptions.ProductNotFoundException;
import com.tbless.inventoryManagementApp.services.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product/")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GetProductController {
    private final ProductService productService;

    @GetMapping("getAddedProduct/{productId}")
    public ResponseEntity<ProductResponse> getAddedProducts(@PathVariable Long productId) throws ProductNotFoundException {
        ProductResponse addedProducts = productService.getProductById(productId);
        return new ResponseEntity<>(addedProducts, HttpStatus.OK);
    }
}
