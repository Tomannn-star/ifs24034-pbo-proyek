package com.example.inventory.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testProductEntity() {
        // 1. Buat Object Entity
        Product product = new Product();

        // 2. Set Data
        Long id = 100L;
        String name = "Telur Ayam";
        int stock = 200;
        String unit = "Butir";
        Double capitalPrice = 1500.0;
        Double price = 2000.0;
        String category = "Sembako";
        String description = "Telur segar dari peternakan";
        String imagePath = "uploads/telur.jpg";

        product.setId(id);
        product.setName(name);
        product.setStock(stock);
        product.setUnit(unit);
        product.setCapitalPrice(capitalPrice);
        product.setPrice(price);
        product.setCategory(category);
        product.setDescription(description);
        product.setImagePath(imagePath);

        // 3. Verifikasi Data (Assert)
        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(stock, product.getStock());
        assertEquals(unit, product.getUnit());
        assertEquals(capitalPrice, product.getCapitalPrice());
        assertEquals(price, product.getPrice());
        assertEquals(category, product.getCategory());
        assertEquals(description, product.getDescription());
        assertEquals(imagePath, product.getImagePath());
    }

    @Test
    void testPrePersist() {
        // Test fitur otomatis tanggal pembuatan
        Product product = new Product();
        
        // Sebelum dipanggil, createdAt harusnya null
        assertNull(product.getCreatedAt());

        // Panggil method prePersist (biasanya dipanggil otomatis oleh JPA, tapi kita panggil manual untuk tes)
        product.prePersist();

        // Setelah dipanggil, createdAt tidak boleh null
        assertNotNull(product.getCreatedAt());
        
        // Pastikan tanggalnya adalah hari ini/sekarang (kurang lebih)
        assertTrue(product.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
    }
}