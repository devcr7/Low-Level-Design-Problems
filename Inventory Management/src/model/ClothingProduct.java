package model;

import enums.ProductCategory;

public class ClothingProduct extends Product{
    private String size;
    private String color;

    public ClothingProduct(String sku, String name, double price, int threshold, ProductCategory category) {
        super(sku, name, price, threshold, category);
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
