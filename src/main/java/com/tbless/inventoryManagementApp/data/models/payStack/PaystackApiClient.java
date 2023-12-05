////package com.tbless.inventoryManagementApp.data.models.payStack;
////
////import com.tbless.inventoryManagementApp.dtos.request.MakePaymentRequest;
////import com.tbless.inventoryManagementApp.dtos.response.MakePaymentResponse;
////import com.tbless.inventoryManagementApp.exceptions.PaystackApiException;
////import lombok.AllArgsConstructor;
////import org.springframework.http.*;
////import org.springframework.web.client.HttpClientErrorException;
////import org.springframework.web.client.RestTemplate;
////
////@AllArgsConstructor
////
////public class PaystackApiClient {
////    private final RestTemplate restTemplate;
////    private final PayStackConfiguration paystackConfiguration;
////
//////    public PaystackApiClient(RestTemplate restTemplate, PayStackConfiguration paystackConfiguration) {
//////        this.restTemplate = restTemplate;
//////        this.paystackConfiguration = paystackConfiguration;
//////    }
////
////    public MakePaymentResponse chargeCard(MakePaymentRequest paymentRequest) throws PaystackApiException {
////        try {
////            HttpHeaders headers = new HttpHeaders();
////            headers.setContentType(MediaType.APPLICATION_JSON);
////            headers.setBearerAuth(paystackConfiguration.getApiKey());
////
////            HttpEntity<MakePaymentRequest> requestEntity = new HttpEntity<>(paymentRequest, headers);
////            ResponseEntity<MakePaymentResponse> responseEntity = restTemplate.exchange(
////                    "https://api.paystack.co/charge",
////                    HttpMethod.POST,
////                    requestEntity,
////                    MakePaymentResponse.class
////            );
////
////            if (responseEntity.getStatusCode() == HttpStatus.OK) {
////                return responseEntity.getBody();
////            } else {
////                throw new PaystackApiException("Failed to process payment. Status code: " + responseEntity.getStatusCode());
////            }
////        } catch (HttpClientErrorException.UnprocessableEntity error) {
////            throw new PaystackApiException("Payment validation failed: " + error.getMessage());
////        } catch (Exception error) {
////            throw new PaystackApiException("An error occurred during payment processing.");
////        }
////    }
////}
//
//
//package com.tbless.inventoryManagementApp.data.models.payStack;
//
//import com.tbless.inventoryManagementApp.dtos.request.MakePaymentRequest;
//import com.tbless.inventoryManagementApp.dtos.response.MakePaymentResponse;
//import com.tbless.inventoryManagementApp.exceptions.PaystackApiException;
//import lombok.AllArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.*;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//@AllArgsConstructor
//public class PaystackApiClient {
//
//    private static final Logger logger = LoggerFactory.getLogger(PaystackApiClient.class);
//
//    private final RestTemplate restTemplate;
//    private final PayStackConfiguration paystackConfiguration;
//
//    public MakePaymentResponse chargeCard(MakePaymentRequest paymentRequest) throws PaystackApiException {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.setBearerAuth(paystackConfiguration.getApiKey());
//
//            HttpEntity<MakePaymentRequest> requestEntity = new HttpEntity<>(paymentRequest, headers);
//            ResponseEntity<MakePaymentResponse> responseEntity = restTemplate.exchange(
//                    "https://api.paystack.co/charge",
//                    HttpMethod.POST,
//                    requestEntity,
//                    MakePaymentResponse.class
//            );
//
//            if (responseEntity.getStatusCode() == HttpStatus.OK) {
//                return responseEntity.getBody();
//            } else {
//                logger.error("Failed to process payment. Status code: {}", responseEntity.getStatusCode());
//                throw new PaystackApiException("Failed to process payment. Status code: " + responseEntity.getStatusCode());
//            }
//        } catch (HttpClientErrorException.UnprocessableEntity error) {
//            logger.error("Payment validation failed: {}", error.getMessage());
//            throw new PaystackApiException("Payment validation failed: " + error.getMessage());
//        } catch (Exception error) {
//            logger.error("An error occurred during payment processing.", error);
//            throw new PaystackApiException("An error occurred during payment processing."+ error);
//        }
//    }
//}
