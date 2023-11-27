package com.tbless.inventoryManagementApp.services.order.helperClasses;

public class CardValidation {

    public static boolean isDebitCardValid(String debitCardNumber) {
        // Check if the card number is numeric and has a valid length
        if (!debitCardNumber.matches("\\d{14,19}")) {
            return false;
        }

        // Perform Luhn algorithm validation
        int sum = 0;
        boolean doubleDigit = false;

        for (int i = debitCardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(debitCardNumber.charAt(i));

            if (doubleDigit) {
                digit *= 2;
                if (digit > 9) {
                    digit = digit % 10 + 1;
                }
            }

            sum += digit;
            doubleDigit = !doubleDigit;
        }

        return sum % 10 == 0;
    }
}
