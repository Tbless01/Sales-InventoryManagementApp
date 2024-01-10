package com.tbless.inventoryManagementApp.services.order;

import com.tbless.inventoryManagementApp.data.models.Order;
import com.tbless.inventoryManagementApp.data.models.Product;
import com.tbless.inventoryManagementApp.data.models.User;
import com.tbless.inventoryManagementApp.data.models.enums.PaymentStatus;
import com.tbless.inventoryManagementApp.data.repository.OrderRepository;
import com.tbless.inventoryManagementApp.dtos.request.MakePaymentRequest;
import com.tbless.inventoryManagementApp.dtos.request.OrderRequest;
import com.tbless.inventoryManagementApp.dtos.request.ProductUpdateRequest;
import com.tbless.inventoryManagementApp.dtos.response.DeleteResponse;
import com.tbless.inventoryManagementApp.dtos.response.MakePaymentResponse;
import com.tbless.inventoryManagementApp.dtos.response.OrderResponse;
import com.tbless.inventoryManagementApp.dtos.response.ProductResponse;
import com.tbless.inventoryManagementApp.exceptions.*;
import com.tbless.inventoryManagementApp.services.product.ProductService;
import com.tbless.inventoryManagementApp.services.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.tbless.inventoryManagementApp.utils.ResponseUtils.ORDER_DELETED_SUCCESSFULLY;
import static com.tbless.inventoryManagementApp.utils.ResponseUtils.ORDER_NOT_FOUND;

@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Override
    public OrderResponse save(OrderRequest orderRequest) {
        Order orderMap = modelMapper.map(orderRequest, Order.class);
        Order order = orderRepository.save(orderMap);
        return buildOwnerProductOrderResponse(order);
    }

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
        Order order = null;
        if (orderRequest.getOrderQuantity() > product.getStock()) {
            throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
        } else if (userIsRegistered && productExists && product.getStock() >= orderRequest.getOrderQuantity()) {
            orderRequest.setEmailAddress(emailAddress);
            orderRequest.setCustomerName(userFound.getFirstName() + " " + userFound.getLastName());
            orderRequest.setDateOrdered(LocalDateTime.now().format(DateTimeFormatter.ofPattern("E dd-MM-yyyy hh:mm:ss a")));
            orderRequest.setPhoneNumber(userFound.getPhoneNumber());
            orderRequest.setTotalAmount(product.getPrice().multiply(new BigDecimal(orderRequest.getOrderQuantity())));
            orderRequest.setPaymentStatus(PaymentStatus.UNPAID);

            order = modelMapper.map(orderRequest, Order.class);
            order.setUniqueId(generateOrderingId(order));
            log.info("Payment status " + order.getPaymentStatus());
            log.info(order.getUniqueId());
            orderRequest.setUniqueId(order.getUniqueId());
            log.info("my Id " + order.getUniqueId());
            product.setStock((product.getStock() - orderRequest.getOrderQuantity()));
            Product productToMap = modelMapper.map(product, Product.class);
            ProductUpdateRequest productUpdateRequest = modelMapper.map(productToMap, ProductUpdateRequest.class);
            productService.updateProduct(product.getId(), productUpdateRequest);
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
    public void makeOrderPayment(String uniqueId) {
        Optional<Order> foundOrder = orderRepository.findOrderByUniqueId(uniqueId);
        if (foundOrder.isPresent()) {
            foundOrder.get().setPaymentStatus(PaymentStatus.SHIPPING);
            orderRepository.save(foundOrder.get());
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
        return orderResponse;
    }

    private static OrderResponse buildOwnerProductOrderResponse(Order order) {
        return OrderResponse.builder()
                .uniqueId(order.getUniqueId())
                .customerName(order.getCustomerName())
                .emailAddress(order.getEmailAddress())
                .phoneNumber(order.getPhoneNumber())
                .date(order.getDateOrdered())
                .product(order.getProduct())
                .totalAmount(order.getTotalAmount())
                .orderQuantity(order.getOrderQuantity())
                .build();
    }
}
