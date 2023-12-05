//package com.tbless.inventoryManagementApp.services.paymentTest;
//
//import com.tbless.inventoryManagementApp.data.models.DebitCardDetails;
//import com.tbless.inventoryManagementApp.data.models.Product;
//import com.tbless.inventoryManagementApp.dtos.request.*;
//import com.tbless.inventoryManagementApp.dtos.response.MakePaymentResponse;
//import com.tbless.inventoryManagementApp.exceptions.*;
//import com.tbless.inventoryManagementApp.services.authentication.RegisterService;
//import com.tbless.inventoryManagementApp.services.order.OrderService;
//import com.tbless.inventoryManagementApp.services.product.ProductService;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.math.BigDecimal;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBootTest
//public class MakePaymentTest {
//
//    @Autowired
//    private RegisterService registerService;
//    @Autowired
//    private OrderService orderService;
//    @Autowired
//    private ProductService productService;
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Test
//    public void testMakePayment() throws PaystackApiException, OrderNotFoundException, UserNotFoundException, InsufficientStockException, ProductNotFoundException, UserRegistrationException, AddDebitCardException {
//
//        RegistrationRequest registrationRequest = new RegistrationRequest();
//        registrationRequest.setEmailAddress("test@example.com");
//        registrationRequest.setPassword("1234");
//        registrationRequest.setGenderType("MALE");
//        registrationRequest.setId(1L);
//        registerService.register(registrationRequest);
//
//        // Create a mock product
//        ProductRequest productRequest = new ProductRequest();
//        productRequest.setEmailAddress(registrationRequest.getEmailAddress());
//        productRequest.setPrice(BigDecimal.valueOf(100));
//        productRequest.setStock(4);
//        productRequest.setName("Gown");
//        productService.createProduct(productRequest);
//        Product product= modelMapper.map(productRequest, Product.class);
//
//        // Create a mock order
//        OrderRequest mockOrder = new OrderRequest();
//        mockOrder.setCustomerName("Moyin");
//        mockOrder.setOrderQuantity(1);
//        mockOrder.setPhoneNumber("08192929389");
//        mockOrder.setEmailAddress(registrationRequest.getEmailAddress());
//        mockOrder.setPaid(false);
//        mockOrder.setProduct(product);
//        mockOrder.getProduct().setId(1L);
//
//        // Place the mock order
//        orderService.placeOrder(mockOrder.getEmailAddress(), mockOrder);
//
//
//        assertNotNull(mockOrder.getUniqueId());
//
//
//        // Mock debit card details
//        AddCardDetailsRequest mockDebitCard = new AddCardDetailsRequest();
//        mockDebitCard.setEmailAddress("test@example.com");
//        mockDebitCard.setDebitCardNumber("5399237041336708");
//        mockDebitCard.setExpiringMonth("12");
//        mockDebitCard.setExpiringYear("23");
//        mockDebitCard.setCvv("123");
//        System.out.println("Test AddCard "+ mockDebitCard.getEmailAddress());
//        orderService.addDebitCard(mockDebitCard.getEmailAddress(), mockDebitCard);
//
//        // Create a mock payment request
//        MakePaymentRequest mockPaymentRequest = new MakePaymentRequest();
//        mockPaymentRequest.setCreditCardNumber(mockDebitCard.getDebitCardNumber());
//        mockPaymentRequest.setExpiringMonth(mockDebitCard.getExpiringMonth());
//        mockPaymentRequest.setExpiringYear(mockDebitCard.getExpiringYear());
//        mockPaymentRequest.setCvv(mockDebitCard.getCvv());
//        mockPaymentRequest.setEmailAddress("test@example.com");
//        mockPaymentRequest.setAmount(mockOrder.getTotalAmount());
//
//        // Create a mock payment response
//        MakePaymentResponse mockResponse = new MakePaymentResponse();
//        // Set mock response values as needed
//
//        // Mocking repository and service calls
//        // (Uncomment and replace with actual mocks as needed)
//
//        // Calling the method to test
//        MakePaymentResponse response = orderService.makePayment(mockOrder.getUniqueId(), mockPaymentRequest);
//
//        // Assertions
//        assertEquals(mockResponse, response);
//        // Add more assertions as needed
//    }
//}
