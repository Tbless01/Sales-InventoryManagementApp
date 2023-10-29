package com.tbless.inventoryManagementApp.services.order;

import com.tbless.inventoryManagementApp.data.models.Order;
import com.tbless.inventoryManagementApp.data.models.User;
import com.tbless.inventoryManagementApp.data.repository.OrderRepository;
import com.tbless.inventoryManagementApp.dtos.request.OrderRequest;
import com.tbless.inventoryManagementApp.dtos.response.OrderResponse;
import com.tbless.inventoryManagementApp.dtos.response.ProductResponse;
import com.tbless.inventoryManagementApp.exceptions.InsufficientStockException;
import com.tbless.inventoryManagementApp.exceptions.ProductNotFoundException;
import com.tbless.inventoryManagementApp.services.product.ProductService;
import com.tbless.inventoryManagementApp.services.user.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public OrderResponse placeOrder(String emailAddress, OrderRequest orderRequest) throws ProductNotFoundException, InsufficientStockException {
        boolean userIsRegistered = userService.findUserByEmailAddress(emailAddress) != null;
        boolean productExists = productService.getProductByName(orderRequest.getProduct().getName()) != null;
        var userFound = userService.findUserByEmailAddress(emailAddress);
        var product = productService.getProductByName(orderRequest.getProduct().getName());
        Order order = getOrder(emailAddress, orderRequest, userIsRegistered, productExists, userFound, product);
        return orderResponse(userFound, order);

    }

    private Order getOrder(String emailAddress, OrderRequest orderRequest, boolean userIsRegistered, boolean productExists, User userFound, ProductResponse product) throws InsufficientStockException {
        Order order = modelMapper.map(orderRequest, Order.class);
        if (userIsRegistered && productExists && product.getStock() > orderRequest.getProduct().getStock()) {
            orderRequest.setEmailAddress(emailAddress);
            orderRequest.setCustomerName(userFound.getFirstName() + " " + userFound.getLastName());
            orderRequest.setDateOrdered(LocalDateTime.now());
            orderRequest.setPhoneNumber(userFound.getPhoneNumber());
            orderRequest.setOrderId(generateOrderingId(order));
            product.setStock(product.getStock() - orderRequest.getOrderQuantity());
            orderRequest.getProduct().setPrice(product.getPrice().multiply(new BigDecimal(orderRequest.getOrderQuantity())));
            orderRepository.save(order);
            if (product.getStock() < orderRequest.getProduct().getStock()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }
        }
        return order;
    }

    private String generateOrderingId(Order orderRequest) {
        var orderIdExists = orderRepository.findOrderByOrderId(orderRequest.getOrderId());
        SecureRandom random = new SecureRandom();
        int orderingID = random.nextInt(10000, 90000);
        if (orderIdExists.isPresent()) generateOrderingId(orderRequest);
        return "#" + orderingID;
    }

    private OrderResponse orderResponse(User user, Order orderItem) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setCustomerName(user.getFirstName() + " " + user.getLastName());
        orderResponse.setPhoneNumber(orderItem.getPhoneNumber());
        orderResponse.setEmailAddress(orderItem.getEmailAddress());
        orderResponse.setDate(LocalDateTime.now());
        orderResponse.setProduct(orderItem.getProduct());
        orderResponse.setOrderId(generateOrderingId(orderItem));
        return orderResponse;
    }
}



