package com.example.inventory.controller;

import com.example.inventory.InventorySystemApplication;
import com.example.inventory.dto.ProductRequest;
import com.example.inventory.entity.Product;
import com.example.inventory.entity.User;
import com.example.inventory.repository.UserRepository;
import com.example.inventory.service.FileStorageService; // Import Service ini
import com.example.inventory.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = InventorySystemApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private UserRepository userRepository;

    // TAMBAHAN: Mock FileStorageService agar tidak error saat load context
    @MockitoBean
    private FileStorageService fileStorageService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testViewHomePage_Authorized() throws Exception {
        User mockUser = new User();
        mockUser.setUsername("admin");
        mockUser.setRole("ADMIN");
        
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(mockUser));
        when(productService.listAll(null)).thenReturn(List.of(new Product()));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(view().name("product"))
                .andExpect(model().attributeExists("listProducts"));
    }

    @Test
    public void testViewHomePage_Unauthorized() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testSaveProduct() throws Exception {
        ProductRequest req = new ProductRequest();
        req.setCapitalPrice(4000.0);

        mockMvc.perform(post("/products/save")
                .with(csrf())
                .flashAttr("productRequest", req)
                .param("name", "New Product")
                .param("stock", "10")
                .param("price", "5000")
                .param("capitalPrice", "4000")) 
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));
    }
}