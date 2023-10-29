package com.tbless.inventoryManagementApp.exceptions;

import com.tbless.inventoryManagementApp.exceptions.InventoryManagementException;

public class ProductAlreadyExistsException extends InventoryManagementException {
    public ProductAlreadyExistsException(String message) {super(message);}
}
