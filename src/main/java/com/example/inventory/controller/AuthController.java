package com.example.inventory.controller;

import com.example.inventory.entity.User;
import com.example.inventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login"; 
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam String username, @RequestParam String password,@RequestParam String role, Model model) {
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Oops! Username tersebut sudah dipakai orang lain. Coba yang lain ya!");
            return "register";
    }

    User newUser = new User();
    newUser.setUsername(username);
    // Enkripsi password sebelum disimpan 
    newUser.setPassword(passwordEncoder.encode(password));
    newUser.setRole(role);
    userRepository.save(newUser);
    return "redirect:/login?registered";
    }
}