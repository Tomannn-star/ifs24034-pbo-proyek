package com.example.inventory.config;

import com.example.inventory.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider; // Tambahkan ini untuk return type yang lebih umum
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService; // Tambahkan ini

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Ubah tipe injeksi menjadi Interface UserDetailsService agar lebih aman
    @Autowired
    private UserDetailsService userDetailsService; 
    
    // Atau jika tetap ingin pakai CustomUserDetailsService, pastikan class itu implements UserDetailsService

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        
        // Error di sini akan hilang jika userDetailsService implements UserDetailsService
        authProvider.setUserDetailsService(userDetailsService);
        
        authProvider.setPasswordEncoder(passwordEncoder);
        
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/uploads/**", "/login", "/register").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login")           
                .defaultSuccessUrl("/products", true) 
                .permitAll()
            )
            .rememberMe(remember -> remember
                .key("rahasiaUnikEasyStock")
                .tokenValiditySeconds(30 * 24 * 60 * 60)
                .rememberMeParameter("remember-me")
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout") 
                .permitAll()
            );

        return http.build();
    }
}