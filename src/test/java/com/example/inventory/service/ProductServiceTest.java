package com.example.inventory.service;

import com.example.inventory.dto.ProductRequest;
import com.example.inventory.entity.Product;
import com.example.inventory.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private ProductService productService;

    @Test
    void testAddProduct_Success() throws IOException {
        // 1. Siapkan Data Palsu (Mock)
        ProductRequest request = new ProductRequest();
        request.setName("Beras");
        request.setStock(10);
        request.setPrice(15000.0);
        request.setUnit("Kg");
        // Mock file gambar
        MockMultipartFile image = new MockMultipartFile("imageFile", "test.jpg", "image/jpeg", "test data".getBytes());
        request.setImageFile(image);

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Beras");

        // 2. Ajari Mockito apa yang harus dilakukan
        when(fileStorageService.storeFile(any())).thenReturn("uuid-test.jpg");
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // 3. Jalankan Method yang mau dites
        Product result = productService.addProduct(request);

        // 4. Verifikasi (Assert)
        assertNotNull(result);
        assertEquals("Beras", result.getName());
        verify(productRepository, times(1)).save(any(Product.class)); // Pastikan save dipanggil 1x
    }

    @Test
    void testGetAllProducts() {
        Product p1 = new Product(); p1.setName("A");
        Product p2 = new Product(); p2.setName("B");

        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Product> list = productService.getAllProducts();

        assertEquals(2, list.size());
    }

    @Test
    void testGetProductById_Found() {
        Product p = new Product();
        p.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(p));

        Product result = productService.getProductById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        // Verifikasi bahwa error dilempar jika ID tidak ada
        assertThrows(RuntimeException.class, () -> {
            productService.getProductById(99L);
        });
    }
}