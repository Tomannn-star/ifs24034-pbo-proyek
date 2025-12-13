package com.example.inventory.config;

import com.example.inventory.entity.User;
import com.example.inventory.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataSeederTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DataSeeder dataSeeder;

    @Test
    void testRun_AdminAlreadyExists() throws Exception {
        // Skenario: User 'admin' SUDAH ADA di database
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(new User()));

        // Jalankan seeder
        dataSeeder.run();

        // Verifikasi: method save TIDAK BOLEH dipanggil
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRun_AdminNotExists() throws Exception {
        // Skenario: User 'admin' BELUM ADA
        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");

        // Jalankan seeder
        dataSeeder.run();

        // Verifikasi: method save HARUS dipanggil 1 kali (membuat admin baru)
        verify(userRepository, times(1)).save(any(User.class));
    }
}