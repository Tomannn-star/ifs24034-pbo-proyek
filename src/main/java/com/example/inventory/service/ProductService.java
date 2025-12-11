package com.example.inventory.service;

import com.example.inventory.dto.ProductRequest;
import com.example.inventory.entity.Product;
import com.example.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileStorageService fileStorageService;

    // --- 1. AMBIL SEMUA DATA ---
    public java.util.List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // --- 2. AMBIL DATA (BISA SEARCH) ---
    public List<Product> listAll(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return productRepository.search(keyword);
        }
        return productRepository.findAll();
    }

    // --- 3. AMBIL 1 DATA (Untuk Edit/Detail) ---
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    // --- 4. FILTER KATEGORI ---
    public List<Product> getByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    // --- 5. TAMBAH PRODUK BARU ---
    public Product addProduct(ProductRequest request) throws IOException {
        Product product = new Product();
        product.setName(request.getName());
        product.setStock(request.getStock());
        product.setCapitalPrice(request.getCapitalPrice());
        product.setUnit(request.getUnit());
        product.setCategory(request.getCategory());

        //Cek: Jika harga jual kosong (inputan supplier)
        if (request.getPrice() == null) {
            product.setPrice(0.0);
        }
        else {
            product.setPrice(request.getPrice());
        }

        product.setDescription(request.getDescription());

        if (request.getImageFile() != null && !request.getImageFile().isEmpty()) {
            String fileName = fileStorageService.storeFile(request.getImageFile());
            product.setImagePath(fileName);
        }
        return productRepository.save(product);
    }
       

    // --- 6. UPDATE PRODUK (Edit) ---
    public Product updateProduct(Long id, ProductRequest request) throws IOException {
        Product product = getProductById(id); 
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setUnit(request.getUnit());
        product.setStock(request.getStock());
        product.setCapitalPrice(request.getCapitalPrice());
        product.setDescription(request.getDescription());

        // Cek jika ada gambar baru yang diupload
        if (request.getImageFile() != null && !request.getImageFile().isEmpty()) {
            String fileName = fileStorageService.storeFile(request.getImageFile());
            product.setImagePath(fileName);
        }

        return productRepository.save(product);
    }

    // --- 7. HAPUS PRODUK ---
    public void deleteProduct(Long id) {
        // Cek dulu apakah data ada
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}