package com.tbless.inventoryManagementApp.services.product;

import com.tbless.inventoryManagementApp.data.models.Product;
import com.tbless.inventoryManagementApp.dtos.request.ProductImageRequest;
import com.tbless.inventoryManagementApp.dtos.request.ProductRequest;
import com.tbless.inventoryManagementApp.dtos.request.ProductUpdateRequest;
import com.tbless.inventoryManagementApp.dtos.response.DeleteResponse;
import com.tbless.inventoryManagementApp.dtos.response.ProductCreatedResponse;
import com.tbless.inventoryManagementApp.dtos.response.ProductResponse;
import com.tbless.inventoryManagementApp.exceptions.ProductNotFoundException;
import com.tbless.inventoryManagementApp.exceptions.SizeOfProductImageExceededException;
import com.tbless.inventoryManagementApp.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductCreatedResponse createProduct(ProductRequest productRequest) throws UserNotFoundException;

    ProductResponse updateProduct(Long productId, ProductUpdateRequest updatedProduct) throws UserNotFoundException, ProductNotFoundException;

    String uploadProductImage(Long productId, ProductImageRequest productImageRequest) throws ProductNotFoundException, SizeOfProductImageExceededException;

    ProductResponse getProductById(Long id) throws ProductNotFoundException;
    Long countNumberOfProductsByEmail(String emailAddress);
    ProductResponse getProductByName(String productName) throws ProductNotFoundException;

    Product findProductByName(String productName) throws ProductNotFoundException;

    List<ProductResponse> getAllAvailableProducts();

    List<ProductResponse> getAllAvailableProductsByEmailAddress(String emailAddress);

    Page<ProductResponse> getAllAvailableProductsByEmailAddressWithPagination(String emailAddress, int offset, int pageSize);

    List<ProductResponse> searchProductsByNameOrEmailAddress(String emailAddress, String keyword);

    List<ProductResponse> getAllAvailableProductsExceptOwnerProduct(String emailAddress);

    List<ProductResponse> searchAllAvailableProductsExceptOwnerProduct(String emailAddress, String keyword);

    DeleteResponse deleteProduct(Long id);

    DeleteResponse autoDeleteWhenProductIsZero(Long id);

    void deleteAll();

}
