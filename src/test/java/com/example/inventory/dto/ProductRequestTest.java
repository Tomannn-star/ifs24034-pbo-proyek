package com.example.inventory.dto;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import static org.junit.jupiter.api.Assertions.*;

class ProductRequestTest {

    @Test
    void testGetterSetter() {
        ProductRequest request = new ProductRequest();
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "content".getBytes());

        request.setName("Gula");
        request.setStock(10);
        request.setCapitalPrice(10000.0);
        request.setPrice(12000.0);
        request.setUnit("Kg");
        request.setCategory("Sembako");
        request.setDescription("Gula Pasir");
        request.setImageFile(file);

        assertEquals("Gula", request.getName());
        assertEquals(10, request.getStock());
        assertEquals(10000.0, request.getCapitalPrice());
        assertEquals(12000.0, request.getPrice());
        assertEquals("Kg", request.getUnit());
        assertEquals("Sembako", request.getCategory());
        assertEquals("Gula Pasir", request.getDescription());
        assertEquals(file, request.getImageFile());
    }
}