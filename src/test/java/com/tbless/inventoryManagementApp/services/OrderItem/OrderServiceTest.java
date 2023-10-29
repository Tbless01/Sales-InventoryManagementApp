package com.tbless.inventoryManagementApp.services.OrderItem;

import com.tbless.inventoryManagementApp.dtos.request.OrderRequest;
import com.tbless.inventoryManagementApp.dtos.request.ProductRequest;
import com.tbless.inventoryManagementApp.dtos.request.RegistrationRequest;
import com.tbless.inventoryManagementApp.dtos.response.ProductCreatedResponse;
import com.tbless.inventoryManagementApp.exceptions.*;
import com.tbless.inventoryManagementApp.services.authentication.RegisterService;
import com.tbless.inventoryManagementApp.services.order.OrderService;
import com.tbless.inventoryManagementApp.services.product.ProductService;
import com.tbless.inventoryManagementApp.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private ProductService productService;

    @Test
    void testThatAUserCanPlaceAnOrder() throws UserRegistrationException, ProductAlreadyExistsException, ProductNotFoundException, UserNotFoundException, InsufficientStockException {
        userService.deleteAll();
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setFirstName("Paul");
        registrationRequest.setLastName("Moses");
        registrationRequest.setGender("MALE");
        registrationRequest.setEmailAddress("Tayo@gmail.com");
        registrationRequest.setPassword("password");

        var registrationResponse = registerService.register(registrationRequest);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Male Shoe");
        productRequest.setPrice(BigDecimal.valueOf(20_000));
        productRequest.setStock(3);
        productRequest.setDescription("Sneakers");

        ProductCreatedResponse productCreatedResponse = productService.createProduct(productRequest);


        OrderRequest orderRequest = new OrderRequest();
        var availableProduct= productService.findProductByName("Male Shoe");
        orderRequest.setProduct(availableProduct);
        orderRequest.setOrderQuantity(6);

        var orderPlaced=orderService.placeOrder("Tayo@gmail.com", orderRequest);

        System.out.println(orderPlaced.getProduct().getPrice());
        System.out.println(orderPlaced.getOrderId());
        System.out.println(orderPlaced.getCustomerName());
        System.out.println(orderPlaced.getDate());
        System.out.println("My Id " + orderPlaced.getOrderId());
        System.out.println(orderPlaced.getProduct().getName());
            System.out.println(orderRequest.getOrderQuantity());
        System.out.println(orderPlaced.getProduct().getDescription());
        System.out.println(orderPlaced.getProduct().toString());
        System.out.println();

        assertThat(orderPlaced).isNotNull();
    }
}
