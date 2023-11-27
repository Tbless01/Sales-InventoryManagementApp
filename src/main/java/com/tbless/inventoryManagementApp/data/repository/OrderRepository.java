package com.tbless.inventoryManagementApp.data.repository;

import com.tbless.inventoryManagementApp.data.models.Order;
import com.tbless.inventoryManagementApp.data.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id);
    Optional<Order> findOrderByProduct(Product product);

    Optional<Order> findOrderByProductEmailAddress(String emailAddress);
    Optional<Order> findOrderByCustomerName(String customerName);
    Optional<Order> findOrderByUniqueId(String OrderId);
}
