package com.example.inventory.service;

import com.example.inventory.dto.ProductRequest;
import com.example.inventory.entity.Product;
import com.example.inventory.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private ProductService productService;

    private ProductRequest productRequest;

    @BeforeEach
    void setUp() {
        productRequest = new ProductRequest();
        productRequest.setName("Test Product");
        productRequest.setPrice(10000.0);
        productRequest.setStock(10);
        productRequest.setCategory("General");
        // PERBAIKAN: Menambahkan capitalPrice agar tidak NullPointerException
        productRequest.setCapitalPrice(8000.0); 
    }

    @Test
    void testAddProduct_Success() throws IOException {
        // Arrange
        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Test Product");
        savedProduct.setPrice(10000.0);
        savedProduct.setCapitalPrice(8000.0);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // Act
        Product result = productService.addProduct(productRequest);

        // Assert
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testAddProduct_WithZeroPrice() throws IOException {
        // Arrange
        productRequest.setPrice(0.0);
        
        Product savedProduct = new Product();
        savedProduct.setPrice(0.0);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // Act
        Product result = productService.addProduct(productRequest);

        // Assert
        assertEquals(0.0, result.getPrice());
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).delete(product);
    }
}