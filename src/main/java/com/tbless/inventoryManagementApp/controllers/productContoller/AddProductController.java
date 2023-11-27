package com.tbless.inventoryManagementApp.controllers.productContoller;

import com.tbless.inventoryManagementApp.dtos.request.ProductRequest;
import com.tbless.inventoryManagementApp.dtos.response.ProductCreatedResponse;
import com.tbless.inventoryManagementApp.exceptions.UserNotFoundException;
import com.tbless.inventoryManagementApp.services.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/product/")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class AddProductController {

    private final ProductService productService;

    @PostMapping("addProduct")
    public ResponseEntity<ProductCreatedResponse> addProduct(@RequestBody ProductRequest productRequest) throws UserNotFoundException {
        return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.OK);
    }
}
