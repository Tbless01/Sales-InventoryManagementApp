package com.tbless.inventoryManagementApp.services.product;

import com.tbless.inventoryManagementApp.data.models.Product;
import com.tbless.inventoryManagementApp.dtos.request.ProductRequest;
import com.tbless.inventoryManagementApp.dtos.request.ProductUpdateRequest;
import com.tbless.inventoryManagementApp.dtos.response.DeleteResponse;
import com.tbless.inventoryManagementApp.dtos.response.ProductCreatedResponse;
import com.tbless.inventoryManagementApp.dtos.response.ProductResponse;
import com.tbless.inventoryManagementApp.exceptions.ProductNotFoundException;
import com.tbless.inventoryManagementApp.exceptions.UserNotFoundException;

import java.util.List;

public interface ProductService {

    ProductCreatedResponse createProduct(ProductRequest productRequest) throws UserNotFoundException;

    ProductResponse updateProduct(Long productId,  ProductUpdateRequest updatedProduct) throws UserNotFoundException, ProductNotFoundException;

    ProductResponse getProductById(Long id) throws ProductNotFoundException;
    ProductResponse getProductByName(String productName) throws ProductNotFoundException;
    Product findProductByName(String productName) throws ProductNotFoundException;
//    ProductResponse getProductByCreatorEmail(String email) throws ProductNotFoundException;
    List<ProductResponse> getAllAvailableProducts();
    List<ProductResponse> getAllAvailableProductsByEmailAddress(String emailAddress);
    List<ProductResponse> getAllAvailableProductsExceptOwnerProduct(String emailAddress);

    DeleteResponse deleteProduct(Long id);
    void deleteAll();

}
