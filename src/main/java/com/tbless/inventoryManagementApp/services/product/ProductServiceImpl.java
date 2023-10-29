package com.tbless.inventoryManagementApp.services.product;

import com.tbless.inventoryManagementApp.data.models.Product;
import com.tbless.inventoryManagementApp.data.repository.ProductRepository;
import com.tbless.inventoryManagementApp.dtos.request.ProductRequest;
import com.tbless.inventoryManagementApp.dtos.request.ProductUpdateRequest;
import com.tbless.inventoryManagementApp.dtos.response.ProductCreatedResponse;
import com.tbless.inventoryManagementApp.dtos.response.ProductResponse;
import com.tbless.inventoryManagementApp.exceptions.ProductNotFoundException;
import com.tbless.inventoryManagementApp.exceptions.ProductAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.tbless.inventoryManagementApp.utils.ExceptionUtils.*;
import static com.tbless.inventoryManagementApp.utils.ResponseUtils.NEW_PRODUCT_SUCCESSFULLY_CREATED;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductCreatedResponse createProduct(ProductRequest productRequest) throws ProductAlreadyExistsException {
        Optional<Product> existingProduct = productRepository.findByName(productRequest.getName());
        if (existingProduct.isPresent())
            throw new ProductAlreadyExistsException(String.format(PRODUCT_ALREADY_EXISTS, productRequest.getName()));
        Product product = modelMapper.map(productRequest, Product.class);
        productRepository.save(product);
        return createdResponse(product.getId());
    }

    private static ProductCreatedResponse createdResponse(Long productId) {
        ProductCreatedResponse createdResponse = new ProductCreatedResponse();
        createdResponse.setId(productId);
        createdResponse.setMessage(NEW_PRODUCT_SUCCESSFULLY_CREATED);
        return createdResponse;
    }

    public ProductResponse updateProduct(String productName, ProductUpdateRequest updatedProduct) throws ProductNotFoundException {
        checkProductExistsWithName(productName);
        Product product = modelMapper.map(updatedProduct, Product.class);
        productRepository.save(product);
        return buildProductResponse(product);
    }

    private void checkProductExistsWithId(Long id) throws ProductNotFoundException {
        try {
            var foundUser = getProductById(id);
        } catch (ProductNotFoundException | NullPointerException e) {
            throw new ProductNotFoundException(String.format(PRODUCT_DOES_NOT_EXIST, id));
        }
    }

    private void checkProductExistsWithName(String name) throws ProductNotFoundException {
        try {
            getProductByName(name);
        } catch (ProductNotFoundException | NullPointerException e) {
            throw new ProductNotFoundException(String.format(PRODUCT_DOES_NOT_EXIST, name.toUpperCase()));
        }
    }

    public List<ProductResponse> getAllAvailableProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductServiceImpl::buildProductResponse)
                .toList();
    }

    @Override
    public void deleteAll() {
        productRepository.deleteAll();
    }

    private static ProductResponse buildProductResponse(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .stock(product.getStock())
                .price(product.getPrice())
                .build();
    }

    @Override
    public ProductResponse getProductById(Long id) throws ProductNotFoundException {
        Optional<Product> foundProduct = productRepository.findById(id);
        Product product = foundProduct.orElseThrow(() -> new ProductNotFoundException(
                String.format(PRODUCT_DOES_NOT_EXIST, id)
        ));
        return buildProductResponse(product);
    }

    @Override
    public ProductResponse getProductByName(String productName) throws ProductNotFoundException {
        Optional<Product> foundProduct = productRepository.findByName(productName);
        Product product = foundProduct.orElseThrow(() -> new ProductNotFoundException(
                String.format(THIS_PRODUCT_DOES_NOT_EXIST, productName)
        ));
        return buildProductResponse(product);
    }

    @Override
    public Product findProductByName(String productName) throws ProductNotFoundException {
        Optional<Product> foundProduct = productRepository.findByName(productName);
        Product product = foundProduct.orElseThrow(() -> new ProductNotFoundException(
                String.format(THIS_PRODUCT_DOES_NOT_EXIST, productName)
        ));
        return product;
    }
}

// Implement other methods, e.g., changeProductPrice, placeOrder, etc.

