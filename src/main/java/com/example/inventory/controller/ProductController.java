package com.example.inventory.controller;

import com.example.inventory.dto.ProductRequest;
import com.example.inventory.entity.Product;
import com.example.inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import com.example.inventory.entity.User;
import com.example.inventory.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String viewHomePage(Model model, Principal principal, @RequestParam(value = "keyword", required = false) String keyword) {
        String username = principal.getName();
        User currenUser = userRepository.findByUsername(username).orElseThrow();

        String role = currenUser.getRole();
        model.addAttribute("userRole", role);
        model.addAttribute("username", username);

        List<Product> listProducts = service.listAll(keyword);
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("keyword", keyword);

        long newItemCount = listProducts.stream()
        .filter(p -> p.getPrice() == 0)
        .count();
        model.addAttribute("notificationCount", newItemCount);

        List<String> names = listProducts.stream().map(Product::getName).collect(Collectors.toList());
        List<Integer> stocks = listProducts.stream().map(Product::getStock).collect(Collectors.toList());

        model.addAttribute("chartLabels", names);
        model.addAttribute("chartData", stocks);

        return "product"; 
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute ProductRequest request) throws IOException {
        service.addProduct(request);
        return "redirect:/products";
    }

     @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = service.getProductById(id);
        model.addAttribute("product", product);
        return "edit_product"; 
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute ProductRequest request) throws IOException {
        service.updateProduct(id, request);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        service.deleteProduct(id);
        return "redirect:/products";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Long id, Model model) {
        Product product = service.getProductById(id);
        model.addAttribute("product", product);
        return "detail_product"; 
    }
}