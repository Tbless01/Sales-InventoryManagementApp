package com.tbless.inventoryManagementApp.services.product;

import com.tbless.inventoryManagementApp.data.models.Product;
import com.tbless.inventoryManagementApp.dtos.request.ProductRequest;
import com.tbless.inventoryManagementApp.dtos.request.ProductUpdateRequest;
import com.tbless.inventoryManagementApp.dtos.response.ProductCreatedResponse;
import com.tbless.inventoryManagementApp.dtos.response.ProductResponse;
import com.tbless.inventoryManagementApp.exceptions.ProductNotFoundException;
import com.tbless.inventoryManagementApp.exceptions.ProductAlreadyExistsException;

import java.util.List;

public interface ProductService {

    ProductCreatedResponse createProduct(ProductRequest productRequest) throws ProductAlreadyExistsException;

    ProductResponse updateProduct(String productName, ProductUpdateRequest updatedProduct) throws ProductNotFoundException;

    ProductResponse getProductById(Long id) throws ProductNotFoundException;
    ProductResponse getProductByName(String productName) throws ProductNotFoundException;
    Product findProductByName(String productName) throws ProductNotFoundException;
    List<ProductResponse> getAllAvailableProducts();
    void deleteAll();

}
