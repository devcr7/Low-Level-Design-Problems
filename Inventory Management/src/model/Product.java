package model;

import enums.ProductCategory;

public abstract class Product {
    private final String sku;
    private final String name;
    private final double price;
    private int quantity;
    private final int threshold;;
    private final ProductCategory category;

    public Product(String sku, String name, double price, int threshold, ProductCategory category) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.threshold = threshold;
        this.category = category;
        this.quantity = 0;
    }

    public String getSku() {
        return this.sku;
    }

    public String getName() {
        return this.name;
    }

    public void addStock(int stock) {
        this.quantity += stock;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void removeStock(int quantity) {
        this.quantity -= quantity;
    }

    public int getThreshold() {
        return this.threshold;
    }
}
