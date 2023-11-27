package com.tbless.inventoryManagementApp.exceptions;

public class OrderNotFoundException extends InventoryManagementException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
