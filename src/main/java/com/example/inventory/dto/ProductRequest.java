package com.example.inventory.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductRequest {

    private String name;
    private int stock;
    private Double capitalPrice;
    private Double price;
    private String unit;
    private String category;
    private String description;
    private MultipartFile imageFile;

    // GETTER & SETTER
    public Double getCapitalPrice() {
        return capitalPrice;
    }

    public void setCapitalPrice(double capitalPrice) {
        this.capitalPrice = capitalPrice;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
