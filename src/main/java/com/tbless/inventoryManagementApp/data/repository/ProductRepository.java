package com.tbless.inventoryManagementApp.data.repository;

import com.tbless.inventoryManagementApp.data.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByNameIgnoreCase(String productName);
    Optional<Product> findById(Long id);
    List<Product> findByEmailAddressIgnoreCase(String productName);

    Optional<Product> findAllById(Long id);
    Optional<Product> deleteProductByName(String name);
}
