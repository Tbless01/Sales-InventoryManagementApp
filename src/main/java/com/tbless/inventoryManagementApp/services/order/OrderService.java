package com.tbless.inventoryManagementApp.services.order;

import com.tbless.inventoryManagementApp.dtos.request.OrderRequest;
import com.tbless.inventoryManagementApp.dtos.response.DeleteResponse;
import com.tbless.inventoryManagementApp.dtos.response.OrderResponse;
import com.tbless.inventoryManagementApp.exceptions.InsufficientStockException;
import com.tbless.inventoryManagementApp.exceptions.OrderNotFoundException;
import com.tbless.inventoryManagementApp.exceptions.ProductNotFoundException;
import com.tbless.inventoryManagementApp.exceptions.UserNotFoundException;

import java.util.List;

public interface OrderService {
    OrderResponse save(OrderRequest orderRequest);
    OrderResponse placeOrder(String emailAddress, OrderRequest orderRequest) throws ProductNotFoundException, InsufficientStockException, UserNotFoundException;
    List<OrderResponse> getAllProductsOrderedByProductOwnerEmailAddress(String emailAddress);
    List<OrderResponse> getAllProductsOrderedByCustomerEmailAddress(String emailAddress);
    OrderResponse getOrderById(Long id) throws OrderNotFoundException;
     void makeOrderPayment(String uniqueId);
     DeleteResponse deleteOrderById(Long id);
}
