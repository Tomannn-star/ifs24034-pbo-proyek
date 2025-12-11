package com.example.inventory.dto;

public class ProductResponse {

    private Long id;
    private String name;
    private int stock;
    private double price;
    private String category;

    // CONSTRUCTOR
    public ProductResponse(Long id, String name, int stock, double price, String category) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.category = category;
    }

    // GETTER ONLY (opsional, agar response tidak bisa diubah)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}
