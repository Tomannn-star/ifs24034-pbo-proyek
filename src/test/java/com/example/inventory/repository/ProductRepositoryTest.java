package com.example.inventory.repository;

import com.example.inventory.InventorySystemApplication;
import com.example.inventory.entity.Product;
import com.example.inventory.service.FileStorageService; // Import ini
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest; // Ganti DataJpaTest
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // Import MockitoBean
import org.springframework.transaction.annotation.Transactional; // Import Transactional

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = InventorySystemApplication.class) // Gunakan SpringBootTest
@Transactional // Agar data di-rollback setelah test selesai (bersih)
@ActiveProfiles("test")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    // Mock FileStorageService agar tidak mengganggu loading context repository
    @MockitoBean
    private FileStorageService fileStorageService;

    @Test
    public void testSaveAndSearchProduct() {
        // Arrange
        Product product = new Product();
        product.setName("Laptop Gaming");
        product.setPrice(15000000.0);
        product.setStock(5);
        product.setCategory("Electronics");
        product.setCapitalPrice(14000000.0);
        
        productRepository.save(product);

        // Act
        List<Product> found = productRepository.search("Laptop");

        // Assert
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getName()).isEqualTo("Laptop Gaming");
    }

    @Test
    public void testFindByCategory() {
        // Arrange
        Product p1 = new Product();
        p1.setName("Mouse");
        p1.setCategory("Aksesoris");
        p1.setCapitalPrice(50000.0);
        productRepository.save(p1);

        // Act
        List<Product> result = productRepository.findByCategory("Aksesoris");

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory()).isEqualTo("Aksesoris");
    }
}