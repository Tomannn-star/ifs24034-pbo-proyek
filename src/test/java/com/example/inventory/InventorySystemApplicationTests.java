package com.example.inventory;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

// PERBAIKAN: Tambahkan parameter classes
@SpringBootTest(classes = InventorySystemApplication.class)
@ActiveProfiles("test")
class InventorySystemApplicationTests {

    @Test
    void contextLoads() {
    }
}