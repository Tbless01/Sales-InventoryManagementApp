package com.tbless.inventoryManagementApp.services.PaystackInt.PaystackService;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tbless.inventoryManagementApp.data.models.Order;
import com.tbless.inventoryManagementApp.data.models.User;
import com.tbless.inventoryManagementApp.data.models.enums.PaymentStatus;
import com.tbless.inventoryManagementApp.dtos.request.OrderRequest;
import com.tbless.inventoryManagementApp.dtos.request.PaymentRequest;
import com.tbless.inventoryManagementApp.dtos.response.OrderResponse;
import com.tbless.inventoryManagementApp.exceptions.OrderNotFoundException;
import com.tbless.inventoryManagementApp.exceptions.UserNotFoundException;
import com.tbless.inventoryManagementApp.services.PaystackInt.httpclient.HttpClient;
import com.tbless.inventoryManagementApp.services.order.OrderService;
import com.tbless.inventoryManagementApp.services.user.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@AllArgsConstructor
public class PaystackServiceImp implements PaystackService {
    private final UserService userService;
    private final HttpClient httpClient;
    private final OrderService orderService;
    private final String SECRET_KEY = "sk_test_92df4c840e3033f629ce899a7bb946935fa749dc";
    private final String VERIFY_ENDPOINT = "https://api.paystack.co/transaction/verify/";
    private final String INITIATE_ENDPOINT = "https://api.paystack.co/transaction/initialize";

    private final ModelMapper modelMapper;

    public String initializeTransaction(PaymentRequest paymentRequest) throws UserNotFoundException {
        User userFound = userService.findUserByEmailAddress(paymentRequest.getEmailAddress());
        if (userFound != null) {
            String email = paymentRequest.getEmailAddress();
            BigDecimal amount = paymentRequest.getAmount().multiply(BigDecimal.valueOf(100));
            String params = "{\"email\":\"" + email + "\",\"amount\":\"" + amount + "\"}";
            String response = httpClient.sendPostRequest(INITIATE_ENDPOINT, SECRET_KEY, params);
            return response;
        } else
            throw new UserNotFoundException("User does not exist");
    }

    public String verifyTransaction(Long id, String reference) throws OrderNotFoundException {
        String endpoint = VERIFY_ENDPOINT + reference;
        String verificationResponse = httpClient.sendGetRequest(endpoint, "Authorization", "Bearer " + SECRET_KEY);

        if (verificationResponse != null) {
            JsonObject jsonResponse = JsonParser.parseString(verificationResponse).getAsJsonObject();
            JsonObject data = jsonResponse.getAsJsonObject("data");
            String status = data.get("status").getAsString();
            String amount = data.get("amount").getAsString();
            BigDecimal creditAmount = new BigDecimal(amount);

            JsonObject json = JsonParser.parseString(verificationResponse).getAsJsonObject();
            String email = json.getAsJsonObject("data").getAsJsonObject("customer").get("email").getAsString();
            if ("success".equals(status)) {
                OrderResponse foundOrder = orderService.getOrderById(id);
                foundOrder.setPaymentStatus(PaymentStatus.PAID);
                OrderRequest mapOrderFound = modelMapper.map(foundOrder, OrderRequest.class);
//                    BigDecimal amount = foundOrder.getTotalAmount();
                orderService.save(mapOrderFound);
                return "Congratulations, payment successful!";
            } else if ("bank_authentication".equals(status))
                return "Bank authentication required for this transaction.";
            else if ("failed".equals(status)) return "Sorry, the transaction was declined.";
            else return "Unknown status for the transaction.";
        }
        return null;
    }


    private String extractReferenceFromResponse(String response) {
        Pattern pattern = Pattern.compile("\"reference\":\"(.*?)\"");
        Matcher matcher = pattern.matcher(response);
        if (matcher.find() && matcher.groupCount() >= 1) {
            return matcher.group(1);
        }
        return null;
    }

}

