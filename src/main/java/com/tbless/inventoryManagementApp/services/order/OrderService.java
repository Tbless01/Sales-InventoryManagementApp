package com.tbless.inventoryManagementApp.services.order;

import com.tbless.inventoryManagementApp.data.models.DebitCardDetails;
import com.tbless.inventoryManagementApp.dtos.request.AddCardDetailsRequest;
import com.tbless.inventoryManagementApp.dtos.request.MakePaymentRequest;
import com.tbless.inventoryManagementApp.dtos.request.OrderRequest;
import com.tbless.inventoryManagementApp.dtos.response.AddCardDetailsResponse;
import com.tbless.inventoryManagementApp.dtos.response.DeleteResponse;
import com.tbless.inventoryManagementApp.dtos.response.MakePaymentResponse;
import com.tbless.inventoryManagementApp.dtos.response.OrderResponse;
import com.tbless.inventoryManagementApp.exceptions.*;

import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(String emailAddress, OrderRequest orderRequest) throws ProductNotFoundException, InsufficientStockException, UserNotFoundException;
//    MakePaymentResponse makePayment(String parentEmail, MakePaymentRequest makePaymentRequest) throws PaystackApiException;
    List<OrderResponse> getAllProductsOrderedByProductOwnerEmailAddress(String emailAddress);
    List<OrderResponse> getAllProductsOrderedByCustomerEmailAddress(String emailAddress);
    OrderResponse getOrderById(Long id) throws OrderNotFoundException;

    //    @Override
    //    public MakePaymentResponse makePayment(String emailAddress,OrderRequest orderRequest, MakePaymentRequest makePaymentRequest) throws PaystackApiException {
    //        DebitCardDetails foundDebitCard = debitCardService.findByUserEmailAddress(emailAddress);
    //        var foundOrder = orderRepository.findById(orderRequest.getId());
    //
    //        MakePaymentRequest paymentRequest = new MakePaymentRequest();
    //        paymentRequest.setCreditCardNumber(foundDebitCard.getDebitCardNumber());
    //        paymentRequest.setExpiringMonth(foundDebitCard.getExpiringMonth());
    //        paymentRequest.setExpiringYear(foundDebitCard.getExpiringYear());
    //        paymentRequest.setCvv(foundDebitCard.getCvv());
    //        paymentRequest.setUserEmail(emailAddress);
    ////        paymentRequest.setAmount(makePaymentRequest.getAmount());
    //        paymentRequest.setAmount(foundOrder.get().getProduct().getPrice());
    //        orderRequest.setPaid(true);
    //        return paystackApiClient.chargeCard(paymentRequest);
    //    }
    MakePaymentResponse makePayment(String uniqueId, MakePaymentRequest makePaymentRequest) throws PaystackApiException, OrderNotFoundException;
    void makeOrderPayment(String uniqueId);
    AddCardDetailsResponse addDebitCard(String emailAddress, AddCardDetailsRequest addCardDetailsRequest) throws AddDebitCardException;

    DebitCardDetails getExistingCardByEmail(String emailAddress) throws NoDebitCarFoundException;
    DeleteResponse deleteOrderById(Long id);
}
