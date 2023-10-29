package com.tbless.inventoryManagementApp.services.product;

import com.tbless.inventoryManagementApp.dtos.request.ProductRequest;
import com.tbless.inventoryManagementApp.dtos.request.ProductUpdateRequest;
import com.tbless.inventoryManagementApp.dtos.response.ProductCreatedResponse;
import com.tbless.inventoryManagementApp.exceptions.ProductAlreadyExistsException;
import com.tbless.inventoryManagementApp.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductServiceTest {
    //    @Autowired
//    private RegisterService registerService;
    @Autowired
    private ProductService productService;
    private ProductCreatedResponse productCreatedResponse;


    @BeforeEach
    public void testAddProduct() throws ProductAlreadyExistsException {
        productService.deleteAll();
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Male Shoe");
        productRequest.setPrice(BigDecimal.valueOf(20_000));
        productRequest.setStock(3);
        productRequest.setDescription("Sneakers");

        productCreatedResponse = productService.createProduct(productRequest);
    }

    @Test
    public void testThatProductCanBeAdded() {
        assertThat(productCreatedResponse).isNotNull();
    }

    @Test
    public void testThatExistingProductCanBeUpdated() throws ProductNotFoundException {
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
        productUpdateRequest.setPrice(BigDecimal.valueOf(30_000));
        productService.updateProduct("Male Shoe", productUpdateRequest);

        System.out.println(productService.getProductByName("Male Shoe").getDescription());

        assertThat(productService.getProductByName("Male Shoe").getPrice().equals(productUpdateRequest.getPrice()));
    }
}
