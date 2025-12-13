package com.example.inventory.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void testUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword("pass");
        user.setRole("SELLER");

        assertEquals(1L, user.getId());
        assertEquals("admin", user.getUsername());
        assertEquals("pass", user.getPassword());
        assertEquals("SELLER", user.getRole());
    }
}