package model;

import enums.ProductCategory;

public class ElectronicsProduct extends Product{
    private String brand;
    private int warrantyPeriod;

    public ElectronicsProduct(String sku, String name, double price, int threshold, ProductCategory category) {
        super(sku, name, price, threshold, category);
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
}
