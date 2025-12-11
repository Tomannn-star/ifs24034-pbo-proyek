package com.example.inventory.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int stock;
    private String unit;

    // HARGA MODAL DARI SUPPLIER
    @Column(name = "capital_price")
    private Double capitalPrice;

    // HARGA JUAL (DITENTUKAN OLEH PEDAGANG)
    private Double price;
    private String category;

    @Column(length = 1000)
    private String description;
    private LocalDateTime createdAt;

    @Column(name = "image_path")
    private String imagePath;


    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    // Getter & Setter
    public Double getCapitalPrice() {
        return capitalPrice;
    }

    public void setCapitalPrice(double capitalPrice) {
        this.capitalPrice = capitalPrice;
    }
    
    public Long getId() { 
        return id; 
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
