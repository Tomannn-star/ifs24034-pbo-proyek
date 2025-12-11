package com.example.inventory.controller;

import com.example.inventory.entity.User;
import com.example.inventory.repository.UserRepository;
import com.example.inventory.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "admin", roles = "SELLER") // Pura-pura login sebagai Seller
    void testViewHomePage_AsSeller() throws Exception {
        // Mock User Login
        User mockUser = new User();
        mockUser.setUsername("admin");
        mockUser.setRole("SELLER");
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(mockUser));

        // Test akses ke halaman /products
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())      // Harapannya status 200 OK
                .andExpect(view().name("product")); // Harapannya membuka file product.html
    }

    @Test
    @WithMockUser(username = "supplier1", roles = "SUPPLIER") // Pura-pura login sebagai Supplier
    void testViewHomePage_AsSupplier() throws Exception {
        User mockUser = new User();
        mockUser.setUsername("supplier1");
        mockUser.setRole("SUPPLIER");
        when(userRepository.findByUsername("supplier1")).thenReturn(Optional.of(mockUser));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("product"));
    }
    
    @Test
    void testAccessWithoutLogin_ShouldRedirect() throws Exception {
        // Akses tanpa @WithMockUser harusnya ditolak/redirect ke login
        mockMvc.perform(get("/products"))
                .andExpect(status().is3xxRedirection()); // 302 Found (Redirect to login)
    }
}