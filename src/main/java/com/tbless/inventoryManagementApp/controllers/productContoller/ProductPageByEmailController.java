package com.tbless.inventoryManagementApp.controllers.productContoller;

import com.tbless.inventoryManagementApp.dtos.response.ProductResponse;
import com.tbless.inventoryManagementApp.services.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product/")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ProductPageByEmailController {

    private final ProductService productService;

    @GetMapping("/products/{emailAddress}/{offset}/{pageSize}")
    public ResponseEntity<Page<ProductResponse>> getAllAvailableProductsByEmailAddressWithPagination(
            @PathVariable String emailAddress,
            @PathVariable int offset,
            @PathVariable int pageSize) {
        try {
            Page<ProductResponse> productPage = productService.getAllAvailableProductsByEmailAddressWithPagination(emailAddress, offset, pageSize);
            return ResponseEntity.ok(productPage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
