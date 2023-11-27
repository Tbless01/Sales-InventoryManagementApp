package com.tbless.inventoryManagementApp.services.order;

import com.tbless.inventoryManagementApp.data.models.DebitCardDetails;
import com.tbless.inventoryManagementApp.data.models.Order;
import com.tbless.inventoryManagementApp.data.models.Product;
import com.tbless.inventoryManagementApp.data.models.User;
import com.tbless.inventoryManagementApp.data.models.payStack.PaystackApiClient;
import com.tbless.inventoryManagementApp.data.repository.OrderRepository;
import com.tbless.inventoryManagementApp.dtos.request.AddCardDetailsRequest;
import com.tbless.inventoryManagementApp.dtos.request.MakePaymentRequest;
import com.tbless.inventoryManagementApp.dtos.request.OrderRequest;
import com.tbless.inventoryManagementApp.dtos.request.ProductUpdateRequest;
import com.tbless.inventoryManagementApp.dtos.response.*;
import com.tbless.inventoryManagementApp.exceptions.*;
import com.tbless.inventoryManagementApp.services.debitCard.DebitCardService;
import com.tbless.inventoryManagementApp.services.order.helperClasses.CardValidation;
import com.tbless.inventoryManagementApp.services.product.ProductService;
import com.tbless.inventoryManagementApp.services.user.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.tbless.inventoryManagementApp.services.order.helperClasses.BuildResponse.buildAddCardResponse;
import static com.tbless.inventoryManagementApp.services.order.helperClasses.BuildResponse.buildFailedToAddCardResponse;
import static com.tbless.inventoryManagementApp.utils.ExceptionUtils.*;
import static com.tbless.inventoryManagementApp.utils.ResponseUtils.ORDER_DELETED_SUCCESSFULLY;
import static com.tbless.inventoryManagementApp.utils.ResponseUtils.ORDER_NOT_FOUND;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final DebitCardService debitCardService;
    private final PaystackApiClient paystackApiClient;


    @Override
    public OrderResponse placeOrder(String emailAddress, OrderRequest orderRequest) throws ProductNotFoundException, InsufficientStockException, UserNotFoundException {
        boolean userIsRegistered = userService.findUserByEmailAddress(emailAddress) != null;
        boolean productExists = productService.getProductById(orderRequest.getProduct().getId()) != null;
        var userFound = userService.findUserByEmailAddress(emailAddress);
        var product = productService.getProductById(orderRequest.getProduct().getId());
        Order order = getOrder(emailAddress, orderRequest, userIsRegistered, productExists, userFound, product);
        return orderResponse(userFound, order);
    }

    private Order getOrder(String emailAddress, OrderRequest orderRequest, boolean userIsRegistered, boolean productExists, User userFound, ProductResponse product) throws InsufficientStockException, UserNotFoundException, ProductNotFoundException {

//        order.setDateOrdered();
        Order order = null;
        if (orderRequest.getOrderQuantity() > product.getStock()) {
            throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
        } else if (userIsRegistered && productExists && product.getStock() >= orderRequest.getOrderQuantity()) {
            orderRequest.setEmailAddress(emailAddress);
            orderRequest.setCustomerName(userFound.getFirstName() + " " + userFound.getLastName());
            orderRequest.setDateOrdered(LocalDateTime.now().format(DateTimeFormatter.ofPattern("E dd-MM-yyyy hh:mm:ss a")));
            orderRequest.setPhoneNumber(userFound.getPhoneNumber());
            orderRequest.setTotalAmount(product.getPrice().multiply(new BigDecimal(orderRequest.getOrderQuantity())));
            orderRequest.setPaid(false);

            order = modelMapper.map(orderRequest, Order.class);
            order.setUniqueId(generateOrderingId(order));
            System.out.println(order.getUniqueId());
            orderRequest.setUniqueId(order.getUniqueId());
            System.out.println("my Id " + order.getUniqueId());
//            orderRequest.getProduct().setStock(product.getStock() - orderRequest.getOrderQuantity());
            product.setStock((product.getStock() - orderRequest.getOrderQuantity()));
            Product productToMap = modelMapper.map(product, Product.class);
//                          if (productToMap.getStock() == 0) productService.deleteProduct(product.getId());
            ProductUpdateRequest productUpdateRequest = modelMapper.map(productToMap, ProductUpdateRequest.class);
            productService.updateProduct(product.getId(), productUpdateRequest);
//            orderRequest.getProduct().setPrice(product.getPrice().multiply(new BigDecimal(orderRequest.getOrderQuantity())));
//            order.getProduct().setPrice(product.getPrice().multiply(new BigDecimal(orderRequest.getOrderQuantity())));
//            order.setPaid(false);

            orderRepository.save(order);
        }
        return order;
    }

    @Override
    public List<OrderResponse> getAllProductsOrderedByProductOwnerEmailAddress(String emailAddress) {
        Optional<Order> orders = orderRepository.findOrderByProductEmailAddress(emailAddress);
        return orders.stream()
                .map(OrderServiceImpl::buildOwnerProductOrderResponse)
                .toList();
    }

    @Override
    public List<OrderResponse> getAllProductsOrderedByCustomerEmailAddress(String emailAddress) {
        Optional<Order> orders = orderRepository.findOrderByProductEmailAddress(emailAddress);
        return orders.stream()
                .map(OrderServiceImpl::buildOwnerProductOrderResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long id) throws OrderNotFoundException {
        Optional<Order> foundOrder = orderRepository.findById(id);
        Order order = foundOrder.orElseThrow(() -> new OrderNotFoundException(
                "Order not found"
        ));
        return buildOwnerProductOrderResponse(order);
    }

    @Override
    public MakePaymentResponse makePayment(String uniqueId, MakePaymentRequest paymentRequest) throws PaystackApiException {
        Optional<Order> foundOrder = orderRepository.findOrderByUniqueId(uniqueId);
        DebitCardDetails foundDebitCard = debitCardService.findByUserEmailAddress(foundOrder.get().getEmailAddress());

//        MakePaymentRequest paymentRequest = new MakePaymentRequest();
//        paymentRequest.setOrderId(foundOrder.get().getOrderId());
        paymentRequest.setCreditCardNumber(foundDebitCard.getDebitCardNumber());
        paymentRequest.setExpiringMonth(foundDebitCard.getExpiringMonth());
        paymentRequest.setExpiringYear(foundDebitCard.getExpiringYear());
        paymentRequest.setCvv(foundDebitCard.getCvv());
        paymentRequest.setEmail(foundOrder.get().getEmailAddress());
        paymentRequest.setEmailAddress(foundOrder.get().getEmailAddress());
        System.out.println("Date Inside make payment "+foundOrder.get().getDateOrdered());
        System.out.println("Quantity Under make payment "+ foundOrder.get().getOrderQuantity());
        System.out.println("UniqueId under make payment " + foundOrder.get().getUniqueId());
//        paymentRequest.setAmount(makePaymentRequest.getAmount());
        foundOrder.get().setPaid(true);
        orderRepository.save(foundOrder.get());
        paymentRequest.setAmount(foundOrder.get().getTotalAmount());
        return paystackApiClient.chargeCard(paymentRequest);
    }

//    @Override
//    public MakePaymentResponse makePayment(String emailAddress, String orderId, MakePaymentRequest makePaymentRequest) throws PaystackApiException {
//        DebitCardDetails foundDebitCard = debitCardService.findByUserEmailAddress(emailAddress);
//        Optional<Order> foundOrder = orderRepository.findOrderByOrderId(orderId);
//
//        MakePaymentRequest paymentRequest = new MakePaymentRequest();
////        paymentRequest.setOrderId(foundOrder.get().getOrderId());
//        paymentRequest.setCreditCardNumber(foundDebitCard.getDebitCardNumber());
//        paymentRequest.setExpiringMonth(foundDebitCard.getExpiringMonth());
//        paymentRequest.setExpiringYear(foundDebitCard.getExpiringYear());
//        paymentRequest.setCvv(foundDebitCard.getCvv());
//        paymentRequest.setUserEmail(emailAddress);
////        paymentRequest.setAmount(makePaymentRequest.getAmount());
////        paymentRequest.setAmount(foundOrder.get().getProduct().getPrice());
//        paymentRequest.setAmount(BigDecimal.valueOf(200));
//        foundOrder.get().setPaid(true);
//        orderRepository.save(foundOrder.get());
//        return paystackApiClient.chargeCard(paymentRequest);
//    }


//@Override
//public MakePaymentResponse makePayment(String emailAddress, MakePaymentRequest makePaymentRequest) throws PaystackApiException {
//    DebitCardDetails foundDebitCard = debitCardService.findByUserEmailAddress(emailAddress);
//    MakePaymentRequest paymentRequest = new MakePaymentRequest();
//    paymentRequest.setCreditCardNumber(foundDebitCard.getDebitCardNumber());
//    paymentRequest.setExpiringMonth(foundDebitCard.getExpiringMonth());
//    paymentRequest.setExpiringYear(foundDebitCard.getExpiringYear());
//    paymentRequest.setCvv(foundDebitCard.getCvv());
//    paymentRequest.setUserEmail(emailAddress);
//    paymentRequest.setAmount(makePaymentRequest.getAmount());
//    return paystackApiClient.chargeCard(paymentRequest);
//}
//@Override
//public MakePaymentResponse makePayment(String emailAddress, MakePaymentRequest makePaymentRequest) throws PaystackApiException {
//    try {
//        // Log relevant information
//        System.out.println("Making payment for user with email: {}"+ emailAddress);
//
//        DebitCardDetails foundDebitCard = debitCardService.findByUserEmailAddress(emailAddress);
//
//        // Log the retrieved card details
//        System.out.println("Found debit card details: {}"+ foundDebitCard);
//
//        MakePaymentRequest paymentRequest = new MakePaymentRequest();
//        paymentRequest.setCreditCardNumber(foundDebitCard.getDebitCardNumber());
//        paymentRequest.setExpiringMonth(foundDebitCard.getExpiringMonth());
//        paymentRequest.setExpiringYear(foundDebitCard.getExpiringYear());
//        paymentRequest.setCvv(foundDebitCard.getCvv());
//        paymentRequest.setUserEmail(emailAddress);
//        paymentRequest.setAmount(makePaymentRequest.getAmount());
//
//        // Log the payment request details
//        System.out.println("Payment request details: {}"+ paymentRequest);
//
//        MakePaymentResponse paymentResponse = paystackApiClient.chargeCard(paymentRequest);
//
//        // Log the payment response
//        System.out.println("Payment successful. Response: {}" + paymentResponse);
//
//        return paymentResponse;
//    } catch (Exception e) {
//        // Log the error
//        System.out.println("Error making payment"+ e);
//
//        // Rethrow or handle the exception based on your requirements
//        throw new PaystackApiException("Error making payment"+ e);
//    }
//}

    @Override
    public AddCardDetailsResponse addDebitCard(String emailAddress, AddCardDetailsRequest addCardDetailsRequest) throws AddDebitCardException {
        DebitCardDetails debitCard = modelMapper.map(addCardDetailsRequest, DebitCardDetails.class);
        var foundUser = userService.findUserByEmailAddress(emailAddress);

        if (foundUser != null) {
            var listOfDebitCards = foundUser.getDebitCardDetails();
            var foundCard = debitCardService.findDebitCard(debitCard);
            if (foundCard != null) {
                throw new AddDebitCardException(FAILED_TO_ADD_CARD);
            }
            boolean validationResult = CardValidation.isDebitCardValid(debitCard.getDebitCardNumber());
            if (!validationResult) {
                throw new AddDebitCardException(INVALID_CARD);
            }
            debitCardService.save(debitCard);
            listOfDebitCards.add(debitCard);

            foundUser.setDebitCardDetails(new ArrayList<>(listOfDebitCards));
            var savedUser = userService.save(foundUser);
            return buildAddCardResponse(savedUser.getId());
        }
        return buildFailedToAddCardResponse(emailAddress);
    }
//    @Override
//    public DebitCardDetails getExistingCardByEmail(String emailAddress) throws NoDebitCarFoundException {
//        DebitCardDetails foundDebitCard = debitCardService.findByUserEmailAddress(emailAddress);
//        if (foundDebitCard == null) {
//            throw new NoDebitCarFoundException(NO_CARD_FOUND);
//        }else return foundDebitCard;
//    }

    @Override
    public DebitCardDetails getExistingCardByEmail(String emailAddress) throws NoDebitCarFoundException {
        System.out.println("Entering getExistingCardByEmail method for email address: {}" + emailAddress);

        DebitCardDetails foundDebitCard = debitCardService.findByUserEmailAddress(emailAddress);

        System.out.println("Result of findByUserEmailAddress for email address {}: {}" + emailAddress + foundDebitCard);

        if (foundDebitCard == null) {
            // Log that no debit card was found
            System.out.println("No debit card found for email address: {}" + emailAddress);
            throw new NoDebitCarFoundException(NO_CARD_FOUND);
        } else {
            // Log that a debit card was found
            System.out.println("Debit card found for email address {}: {}" + emailAddress + foundDebitCard.toString());
            return foundDebitCard;
        }
    }

    @Override
    public DeleteResponse deleteOrderById(Long id) {
        Optional<Order> orderFound = orderRepository.findById(id);
        if (orderFound.isEmpty()) throw new NullPointerException(String.format(ORDER_NOT_FOUND));
        orderRepository.deleteById(id);
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setMessage(ORDER_DELETED_SUCCESSFULLY);
        deleteResponse.setId(id);
        return deleteResponse;
    }

    private String generateOrderingId(Order orderRequest) {
        var orderIdExists = orderRepository.findOrderByUniqueId(orderRequest.getUniqueId());
        SecureRandom random = new SecureRandom();
        int orderingID = random.nextInt(10000, 90000);
        if (orderIdExists.isPresent()) generateOrderingId(orderRequest);
        return "#" + orderingID;
    }

    private OrderResponse orderResponse(User user, Order orderItem) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setUniqueId(orderItem.getUniqueId());
        orderResponse.setCustomerName(user.getFirstName() + " " + user.getLastName());
        orderResponse.setPhoneNumber(orderItem.getPhoneNumber());
        orderResponse.setEmailAddress(orderItem.getEmailAddress());
        orderResponse.setDate(orderItem.getDateOrdered());
        orderResponse.setProduct(orderItem.getProduct());
        System.out.println("Inside response my Id " + orderItem.getUniqueId());
        System.out.println(orderItem.getDateOrdered());
        System.out.println("Get order from order table " + orderItem.getUniqueId());
//        orderResponse.setOrderId(orderRepository.findOrderByOrderId(orderItem.getOrderId()).get().getOrderId());
        return orderResponse;
    }

    private static OrderResponse buildOwnerProductOrderResponse(Order order) {
        System.out.println(order.getUniqueId());
        return OrderResponse.builder()
                .uniqueId(order.getUniqueId())
                .customerName(order.getCustomerName())
                .emailAddress(order.getEmailAddress())
                .phoneNumber(order.getPhoneNumber())
                .date(order.getDateOrdered())
                .product(order.getProduct())
                .build();
    }
}
