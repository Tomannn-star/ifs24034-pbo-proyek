package com.example.inventory.service;

import com.example.inventory.repository.UserRepository; // Sesuaikan dengan repo Anda
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService; // WAJIB ADA
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService { // <--- PERHATIKAN INI

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Logika untuk mengambil user dari database
        // Contoh:
        // User user = userRepository.findByUsername(username)
        //         .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // return new org.springframework.security.core.userdetails.User(
        //         user.getUsername(), 
        //         user.getPassword(), 
        //         user.getAuthorities());
        return null; // Ganti dengan return user asli Anda
    }
}