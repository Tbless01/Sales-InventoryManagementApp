package com.tbless.inventoryManagementApp.data.models.enums;

public enum PaymentStatus {
    PAID("PAID"),
    UNPAID("UNPAID"),
    SHIPPING("SHIPPING");
    private final String method;

    PaymentStatus(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
