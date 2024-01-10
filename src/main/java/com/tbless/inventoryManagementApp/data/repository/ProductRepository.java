package com.tbless.inventoryManagementApp.data.repository;

import com.tbless.inventoryManagementApp.data.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> searchAllByNameContainingIgnoreCase(String name);
    List<Product> searchAllByEmailAddressContainingIgnoreCaseAndNameContainingIgnoreCase(String emailAddress, String keyword);
    Long countProductByEmailAddress(String emailAddress);
    @Query("SELECT p FROM Product p WHERE p.emailAddress = :emailAddress")
    Page<Product> findAllByEmailAddress(String emailAddress, Pageable pageable);
    Optional<Product> findByNameIgnoreCase(String productName);
    Optional<Product> findById(Long id);
    List<Product> findByEmailAddressIgnoreCase(String productName);
    Optional<Product> findAllById(Long id);
    Optional<Product> deleteProductByName(String name);
}
