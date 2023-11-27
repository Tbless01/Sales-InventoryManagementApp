package com.tbless.inventoryManagementApp.controllers.productContoller;
//
//public class ProductUpdateController {
//
//}
// ProductController.java

import com.tbless.inventoryManagementApp.dtos.request.ProductUpdateRequest;
import com.tbless.inventoryManagementApp.dtos.response.ProductResponse;
import com.tbless.inventoryManagementApp.exceptions.ProductNotFoundException;
import com.tbless.inventoryManagementApp.exceptions.UserNotFoundException;
import com.tbless.inventoryManagementApp.services.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/product/")
@CrossOrigin(origins = "*")
public class ProductUpdateController {
    private final ProductService productService;

    @PutMapping("update/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateRequest updatedProduct) throws UserNotFoundException, ProductNotFoundException {
        ProductResponse savedProduct = productService.updateProduct(productId, updatedProduct);
        return new  ResponseEntity<>(savedProduct, HttpStatus.OK);
    }
}