package com.tbless.inventoryManagementApp.services.product;

import com.tbless.inventoryManagementApp.dtos.request.ProductRequest;
import com.tbless.inventoryManagementApp.dtos.request.ProductUpdateRequest;
import com.tbless.inventoryManagementApp.dtos.request.RegistrationRequest;
import com.tbless.inventoryManagementApp.dtos.response.ProductCreatedResponse;
import com.tbless.inventoryManagementApp.dtos.response.ProductResponse;
import com.tbless.inventoryManagementApp.exceptions.ProductNotFoundException;
import com.tbless.inventoryManagementApp.exceptions.UserNotFoundException;
import com.tbless.inventoryManagementApp.exceptions.UserRegistrationException;
import com.tbless.inventoryManagementApp.services.authentication.RegisterService;
import com.tbless.inventoryManagementApp.services.user.UserService;
import com.tbless.inventoryManagementApp.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
@TestPropertySource(locations = "classpath:application-test.properties")
public class ProductServiceTest {
    @Autowired
    private RegisterService registerService;
    @Autowired
    private ProductService productService;
    private ProductCreatedResponse productCreatedResponse;
    private UserService userService;

//    @BeforeEach
//    public void testAddProduct() throws UserRegistrationException, UserNotFoundException {
////        userService.deleteAll();
//
//        RegistrationRequest registrationRequest = new RegistrationRequest();
//        registrationRequest.setFirstName("Odun");
//        registrationRequest.setLastName("Ben");
//        registrationRequest.setEmailAddress("menodun@gmail.com");
//        registrationRequest.setPhoneNumber("07087605675");
//        registrationRequest.setPassword("1234");
//        ApiResponse userRegistered = registerService.register(registrationRequest);
//
////        productService.deleteAll();
//        ProductRequest productRequest = new ProductRequest();
//        productRequest.setEmailAddress(registrationRequest.getEmailAddress());
//        productRequest.setName("Male Shoe");
//        productRequest.setPrice(BigDecimal.valueOf(20_000));
//        productRequest.setStock(3);
//        productRequest.setDescription("Sneakers");
//
//        productCreatedResponse = productService.createProduct(productRequest);
//    }

    @Test
    public void testThatProductCanBeAdded() throws UserNotFoundException, UserRegistrationException {

        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName("Odun");
        registrationRequest.setLastName("Ben");
        registrationRequest.setEmailAddress("menodun@gmail.com");
        registrationRequest.setPhoneNumber("07087605675");
        registrationRequest.setPassword("1234");
        ApiResponse userRegistered = registerService.register(registrationRequest);

//        productService.deleteAll();
        ProductRequest productRequest = new ProductRequest();
        productRequest.setEmailAddress(registrationRequest.getEmailAddress());
        productRequest.setName("Male Shoe");
        productRequest.setPrice(BigDecimal.valueOf(20_000));
        productRequest.setStock(3);
        productRequest.setDescription("Sneakers");

        productCreatedResponse = productService.createProduct(productRequest);
        assertThat(productCreatedResponse).isNotNull();
    }

    @Test
    public void testThatExistingProductCanBeUpdated() throws ProductNotFoundException, UserNotFoundException, UserRegistrationException {

        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName("King");
        registrationRequest.setLastName("Ben");
        registrationRequest.setEmailAddress("prof@gmail.com");
        registrationRequest.setPhoneNumber("07087605675");
        registrationRequest.setPassword("1234");
        ApiResponse userRegistered = registerService.register(registrationRequest);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setEmailAddress(registrationRequest.getEmailAddress());
        productRequest.setName("Shoe");
        productRequest.setPrice(BigDecimal.valueOf(20000));
        productRequest.setStock(3);
        productRequest.setDescription("Sneakers");
        ProductCreatedResponse productCreatedResponse = productService.createProduct(productRequest);
        Long productId = productCreatedResponse.getId();

        int newStock = 5;
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
        productUpdateRequest.setStock(newStock);
        productService.updateProduct(productId, productUpdateRequest);

        ProductResponse updatedProduct = productService.getProductById(productId);
        assertEquals(newStock, updatedProduct.getStock());
    }
}