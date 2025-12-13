package com.example.inventory.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        // 1. Siapkan pesan error
        String expectedMessage = "Barang tidak ditemukan";

        // 2. Buat exception baru
        NotFoundException exception = new NotFoundException(expectedMessage);

        // 3. Verifikasi apakah pesannya sama
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
        
        // Verifikasi bahwa ini adalah turunan RuntimeException
        assertTrue(exception instanceof RuntimeException);
    }
}