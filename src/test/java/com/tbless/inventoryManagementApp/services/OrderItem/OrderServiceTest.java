//package com.tbless.inventoryManagementApp.services.OrderItem;
//
//import com.tbless.inventoryManagementApp.dtos.request.OrderRequest;
//import com.tbless.inventoryManagementApp.dtos.request.ProductRequest;
//import com.tbless.inventoryManagementApp.dtos.request.RegistrationRequest;
//import com.tbless.inventoryManagementApp.dtos.response.ProductCreatedResponse;
//import com.tbless.inventoryManagementApp.exceptions.*;
//import com.tbless.inventoryManagementApp.services.authentication.RegisterService;
//import com.tbless.inventoryManagementApp.services.order.OrderService;
//import com.tbless.inventoryManagementApp.services.product.ProductService;
//import com.tbless.inventoryManagementApp.services.user.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.math.BigDecimal;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Slf4j
//@SpringBootTest
//public class OrderServiceTest {
//    @Autowired
//    private OrderService orderService;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private RegisterService registerService;
//    @Autowired
//    private ProductService productService;
//
//    @Test
//    void testThatAUserCanPlaceAnOrder() throws UserRegistrationException, ProductNotFoundException, UserNotFoundException, InsufficientStockException, BadNetworkException {
//        userService.deleteAll();
//        RegistrationRequest registrationRequest = new RegistrationRequest();
//        registrationRequest.setFirstName("Paul");
//        registrationRequest.setLastName("Moses");
//        registrationRequest.setGenderType("MALE");
//        registrationRequest.setEmailAddress("Tayo@gmail.com");
//        registrationRequest.setPassword("password");
//
//        var registrationResponse = registerService.register(registrationRequest);
//
//        RegistrationRequest registrationRequestBuyer = new RegistrationRequest();
//        registrationRequestBuyer.setFirstName("Prof");
//        registrationRequestBuyer.setLastName("Ola");
//        registrationRequestBuyer.setGenderType("MALE");
//        registrationRequestBuyer.setEmailAddress("profBaba@gmail.com");
//        registrationRequestBuyer.setPassword("password");
//
//        var registrationResponseBuyer = registerService.register(registrationRequestBuyer);
//        ProductRequest productRequest = new ProductRequest();
//        productRequest.setEmailAddress(registrationRequest.getEmailAddress());
//        productRequest.setName("Male Shoe");
//        productRequest.setPrice(BigDecimal.valueOf(20_000));
//        productRequest.setStock(9);
//        productRequest.setDescription("Sneakers");
//
//        ProductCreatedResponse productCreatedResponse = productService.createProduct( productRequest);
//
//
//        OrderRequest orderRequest = new OrderRequest();
//        var availableProduct= productService.findProductByName("Male Shoe");
//        orderRequest.setEmailAddress(registrationRequestBuyer.getEmailAddress());
//        orderRequest.setProduct(availableProduct);
//        orderRequest.setOrderQuantity(4);
//
//        var orderPlaced=orderService.placeOrder(orderRequest.getEmailAddress(), orderRequest);
//
//        System.out.println(orderPlaced.getProduct().getPrice());
//        System.out.println(orderPlaced.getUniqueId());
//        System.out.println("Customer's name "+orderPlaced.getCustomerName());
//        System.out.println(orderPlaced.getDate());
//        System.out.println("My Id " + orderPlaced.getUniqueId());
//        System.out.println(orderPlaced.getProduct().getName());
//        System.out.println("Owner of Product gmail "+ orderPlaced.getProduct().getEmailAddress());
//            System.out.println(orderRequest.getOrderQuantity());
//        System.out.println(orderPlaced.getProduct().getDescription());
//        System.out.println(orderPlaced.getProduct().toString());
//        System.out.println("Remaining product in stock "+ orderPlaced.getProduct().getStock());
//        System.out.println();
//
//        assertThat(orderPlaced).isNotNull();
//    }
//}
