package com.tbless.inventoryManagementApp.controllers.productContoller;

import com.tbless.inventoryManagementApp.dtos.response.DeleteResponse;
import com.tbless.inventoryManagementApp.services.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product/")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class DeleteController {
    private final ProductService productService;

    @DeleteMapping("delete/{id}")
    public ResponseEntity<DeleteResponse> deleteProduct(@PathVariable Long id) {
        var deleteResponse = productService.deleteProduct(id);
        return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
    }
    @DeleteMapping("deleteWhenZero/{productId}")
    public ResponseEntity<DeleteResponse> deleteProductWhenZero(@PathVariable Long productId) {
        var deleteResponse = productService.autoDeleteWhenProductIsZero(productId);
        return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
    }
}
