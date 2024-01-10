package com.tbless.inventoryManagementApp.controllers.productContoller;

import com.tbless.inventoryManagementApp.dtos.response.ProductResponse;
import com.tbless.inventoryManagementApp.services.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/product/")
@CrossOrigin(origins = "*")
public class SearchProductController {
    private final ProductService productService;

    @GetMapping("/search/{emailAddress}/{keyword}")
    public ResponseEntity<List<ProductResponse>> searchProductWithNameOrEmailAddress(@PathVariable String emailAddress,@PathVariable String keyword){
        return new ResponseEntity<>(productService.searchProductsByNameOrEmailAddress(emailAddress, keyword), HttpStatus.OK);
    }
}
