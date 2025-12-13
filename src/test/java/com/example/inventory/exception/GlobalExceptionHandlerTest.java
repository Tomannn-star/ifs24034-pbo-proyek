package com.example.inventory.exception;

import com.example.inventory.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleNotFound() {
        // 1. Siapkan Exception tiruan
        String errorMsg = "Produk dengan ID 999 tidak ada";
        NotFoundException ex = new NotFoundException(errorMsg);

        // 2. Panggil method handleNotFound secara manual
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleNotFound(ex);

        // 3. Verifikasi Status Code harus 404 NOT_FOUND
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getStatusCode().value());

        // 4. Verifikasi Body (Pesan Error)
        ErrorResponse body = response.getBody();
        assertNotNull(body);
        
        // Asumsi: ErrorResponse punya getter. 
        // Kita cek apakah pesan di dalam body sama dengan pesan exception
        // (Sesuaikan getter ini dengan file ErrorResponse.java Anda, biasanya getMessage() atau sejenisnya)
        // assertEquals(errorMsg, body.getMessage()); 
    }
}