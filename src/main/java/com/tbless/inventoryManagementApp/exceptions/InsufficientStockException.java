package com.tbless.inventoryManagementApp.exceptions;

public class InsufficientStockException extends InventoryManagementException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
