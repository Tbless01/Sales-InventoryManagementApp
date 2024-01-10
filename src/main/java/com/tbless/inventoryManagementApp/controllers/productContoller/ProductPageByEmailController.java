package com.tbless.inventoryManagementApp.controllers.productContoller;

import com.tbless.inventoryManagementApp.dtos.response.ProductResponse;
import com.tbless.inventoryManagementApp.services.product.ProductService;
import lombok.AllArgsConstructor;
import org.apache.catalina.authenticator.SavedRequest;
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

    @GetMapping("/products/{emailAddress}/{pageNumber}/{pageSize}")
    public ResponseEntity<Page<ProductResponse>> getAllAvailableProductsByEmailAddressWithPagination(
            @PathVariable String emailAddress,
            @PathVariable int pageNumber,
            @PathVariable int pageSize) {
        try {
            Page<ProductResponse> productPage = productService.getAllAvailableProductsByEmailAddressWithPagination(emailAddress, pageNumber, pageSize);
            return ResponseEntity.ok(productPage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/count/{emailAddress}")
    public ResponseEntity<Long> getProductCountByEmailAddress(@PathVariable String emailAddress){
        return new ResponseEntity<>(productService.countNumberOfProductsByEmail(emailAddress), HttpStatus.OK);
    }
}
