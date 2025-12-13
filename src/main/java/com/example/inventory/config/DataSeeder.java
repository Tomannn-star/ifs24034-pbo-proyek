package com.example.inventory.config;

import com.example.inventory.entity.User;
import com.example.inventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile; // WAJIB ADA
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("!test") // Hanya jalan jika BUKAN mode test
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin123")); 
            user.setRole("ADMIN");
            
            userRepository.save(user);
            System.out.println("User ADMIN berhasil dibuat: Username 'admin', Password 'admin123'");
        }
    }
}