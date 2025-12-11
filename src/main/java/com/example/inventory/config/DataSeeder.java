package com.example.inventory.config;

import com.example.inventory.entity.User;
import com.example.inventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Cek apakah user 'admin' sudah ada? Jika belum, buat baru.
        if (userRepository.findByUsername("admin").isEmpty()) {
            User user = new User();
            user.setUsername("admin");
            // Password 'admin123' dienkripsi
            user.setPassword(passwordEncoder.encode("admin123")); 
            user.setRole("ADMIN");
            
            userRepository.save(user);
            System.out.println("User ADMIN berhasil dibuat: Username 'admin', Password 'admin123'");
        }
    }
}