package com.tbless.inventoryManagementApp.services.order;

import com.tbless.inventoryManagementApp.dtos.request.OrderRequest;
import com.tbless.inventoryManagementApp.dtos.response.OrderResponse;
import com.tbless.inventoryManagementApp.exceptions.InsufficientStockException;
import com.tbless.inventoryManagementApp.exceptions.ProductNotFoundException;
import com.tbless.inventoryManagementApp.exceptions.UserNotFoundException;

public interface OrderService {
    OrderResponse placeOrder(String emailAddress, OrderRequest orderRequest) throws ProductNotFoundException, InsufficientStockException, UserNotFoundException;

}
