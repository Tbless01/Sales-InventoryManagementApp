package com.tbless.inventoryManagementApp.services.product;

import com.tbless.inventoryManagementApp.dtos.request.ProductRequest;
import com.tbless.inventoryManagementApp.dtos.request.ProductUpdateRequest;
import com.tbless.inventoryManagementApp.dtos.request.RegistrationRequest;
import com.tbless.inventoryManagementApp.dtos.response.ProductCreatedResponse;
import com.tbless.inventoryManagementApp.exceptions.ProductNotFoundException;
import com.tbless.inventoryManagementApp.exceptions.UserNotFoundException;
import com.tbless.inventoryManagementApp.exceptions.UserRegistrationException;
import com.tbless.inventoryManagementApp.services.authentication.RegisterService;
import com.tbless.inventoryManagementApp.services.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private RegisterService registerService;
    @Autowired
    private ProductService productService;
    private ProductCreatedResponse productCreatedResponse;
    @Autowired
    private  UserService userService;


    @BeforeEach
    public void testAddProduct() throws UserRegistrationException, UserNotFoundException {
        userService.deleteAll();

        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName("Odun");
        registrationRequest.setLastName("Ben");
        registrationRequest.setEmailAddress("menodun@gmail.com");
        registrationRequest.setPassword("1234");
        registrationRequest.setGenderType("MALE");
        var userRegistered = registerService.register(registrationRequest);

        productService.deleteAll();
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Male Shoe");
        productRequest.setPrice(BigDecimal.valueOf(20_000));
        productRequest.setStock(3);
        productRequest.setDescription("Sneakers");

        productCreatedResponse = productService.createProduct( productRequest);
    }

    @Test
    public void testThatProductCanBeAdded() {
        assertThat(productCreatedResponse).isNotNull();
    }

    @Test
    public void testThatExistingProductCanBeUpdated() throws ProductNotFoundException, UserNotFoundException {
        var productId = productCreatedResponse.getId();
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
        productUpdateRequest.setPrice(BigDecimal.valueOf(30_000));
        productService.updateProduct(productId, productUpdateRequest);

        System.out.println(productService.getProductByName("Male Shoe").getDescription());

        assertThat(productService.getProductByName("Male Shoe").getPrice().equals(productUpdateRequest.getPrice()));
    }
}
