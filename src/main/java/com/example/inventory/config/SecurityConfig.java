package com.example.inventory.config;

import com.example.inventory.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    // Inject PasswordEncoder dari Main Class
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // PERBAIKAN DI SINI:
        // Gunakan konstruktor kosong ()
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        
        // Lalu set userDetailsService menggunakan method setter
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