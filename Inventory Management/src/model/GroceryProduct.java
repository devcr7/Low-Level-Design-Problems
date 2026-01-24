package model;

import enums.ProductCategory;

import java.util.Date;

public class GroceryProduct extends Product {
    private Date expirationDate;
    private Boolean refrigerated;

    public GroceryProduct(String sku, String name, double price, int threshold, ProductCategory category) {
        super(sku, name, price, threshold, category);
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setRefrigerated(Boolean refrigerated) {
        this.refrigerated = refrigerated;
    }
}
