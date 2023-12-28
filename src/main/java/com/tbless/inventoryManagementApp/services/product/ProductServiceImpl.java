package com.tbless.inventoryManagementApp.services.product;

import com.tbless.inventoryManagementApp.data.models.Product;
import com.tbless.inventoryManagementApp.data.repository.ProductRepository;
import com.tbless.inventoryManagementApp.dtos.request.ProductImageRequest;
import com.tbless.inventoryManagementApp.dtos.request.ProductRequest;
import com.tbless.inventoryManagementApp.dtos.request.ProductUpdateRequest;
import com.tbless.inventoryManagementApp.dtos.response.DeleteResponse;
import com.tbless.inventoryManagementApp.dtos.response.ProductCreatedResponse;
import com.tbless.inventoryManagementApp.dtos.response.ProductResponse;
import com.tbless.inventoryManagementApp.exceptions.ProductNotFoundException;
import com.tbless.inventoryManagementApp.exceptions.SizeOfProductImageExceededException;
import com.tbless.inventoryManagementApp.exceptions.UserNotFoundException;
import com.tbless.inventoryManagementApp.services.user.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.tbless.inventoryManagementApp.utils.ExceptionUtils.*;
import static com.tbless.inventoryManagementApp.utils.ResponseUtils.*;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public ProductCreatedResponse createProduct(ProductRequest productRequest) throws UserNotFoundException {
        boolean userExists = userService.findUserByEmailAddress(productRequest.getEmailAddress()) != null;
        if (!userExists) throw new UserNotFoundException(USER_NOT_FOUND);
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

    public ProductResponse updateProduct(Long productId, ProductUpdateRequest updatedProduct) throws ProductNotFoundException {
        boolean productExists = productRepository.findById(productId).isPresent();
        Optional<Product> existingProduct = productRepository.findById(productId);
        if (!productExists) throw new ProductNotFoundException(PRODUCT_NOT_FOUND);
        existingProduct.get().setPrice(updatedProduct.getPrice());
        existingProduct.get().setStock(updatedProduct.getStock());
        existingProduct.get().setDescription(updatedProduct.getDescription());
        Product product = modelMapper.map(existingProduct, Product.class);
        productRepository.save(product);
        return buildProductResponse(product);
    }

    @Override
    public String uploadProductImage(Long productId, ProductImageRequest productImageRequest) throws ProductNotFoundException, SizeOfProductImageExceededException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
        if (product.getImageUrl().size() >= 20)
            throw new SizeOfProductImageExceededException(PRODUCT_IMAGE_SIZE_EXCEEDED);
        List<String> imageUrls = product.getImageUrl();
        imageUrls.add(productImageRequest.getImageUrl());
        product.setImageUrl(imageUrls);
        productRepository.save(product);
        return productImageRequest.getImageUrl();
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

    public List<ProductResponse> getAllAvailableProductsExceptOwnerProduct(String emailAddress) {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .filter(product -> !product.getEmailAddress().equals(emailAddress))
                .map(ProductServiceImpl::buildProductResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> getAllAvailableProductsByEmailAddress(String emailAddress) {
        List<Product> products = productRepository.findByEmailAddressIgnoreCase(emailAddress);
        return products.stream()
                .map(ProductServiceImpl::buildProductResponse)
                .toList();
    }

    public Page<ProductResponse> getAllAvailableProductsByEmailAddressWithPagination(String emailAddress, int offset, int pageSize) {
        Page<Product> products = productRepository.findAll(PageRequest.of(offset, pageSize));
        List<ProductResponse> productResponses = products
                .filter(product -> !product.getEmailAddress().equals(emailAddress))
                .map(this::buildProductPaginationResponse)
                .toList();
        return new PageImpl<>(productResponses, products.getPageable(), products.getTotalElements());
    }

    private ProductResponse buildProductPaginationResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .imageUrl(product.getImageUrl())
                .description(product.getDescription())
                .stock(product.getStock())
                .price(product.getPrice())
                .build();
    }

    @Override
    public DeleteResponse deleteProduct(Long id) {
        Optional<Product> productFound = productRepository.findById(id);
        if (productFound.isEmpty()) throw new NullPointerException(String.format(PRODUCT_NOT_FOUND));
        productRepository.deleteById(id);
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setMessage(PRODUCT_DELETED_SUCCESSFULLY);
        deleteResponse.setId(id);
        return deleteResponse;
    }

    @Override
    public DeleteResponse autoDeleteWhenProductIsZero(Long id) {
        Optional<Product> productFound = productRepository.findById(id);
        if (productFound.get().getStock() == 0) productRepository.deleteById(id);
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setMessage(PRODUCT_DELETED_SUCCESSFULLY);
        deleteResponse.setId(id);
        return deleteResponse;
    }

    @Override
    public void deleteAll() {
        productRepository.deleteAll();
    }

    private static ProductResponse buildProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .imageUrl(product.getImageUrl())
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
        Optional<Product> foundProduct = productRepository.findByNameIgnoreCase(productName);
        Product product = foundProduct.orElseThrow(() -> new ProductNotFoundException(
                String.format(THIS_PRODUCT_DOES_NOT_EXIST, productName)
        ));
        return buildProductResponse(product);
    }

    @Override
    public Product findProductByName(String productName) throws ProductNotFoundException {
        Optional<Product> foundProduct = productRepository.findByNameIgnoreCase(productName);
        Product product = foundProduct.orElseThrow(() -> new ProductNotFoundException(
                String.format(THIS_PRODUCT_DOES_NOT_EXIST, productName)
        ));
        return product;
    }


//    @Override
//    public Product getProductByCreatorEmail(String email) throws ProductNotFoundException {
//        Optional<Product> foundProduct = productRepository.findByCreatorEmailAddress(email);
//        Product product = foundProduct.orElseThrow(() -> new ProductNotFoundException(
//                String.format(YOU_DO_NOT_HAVE_ANY_PRODUCT_AT_THE_MOMENT, email)
//        ));
//        return product;
//    }
}
