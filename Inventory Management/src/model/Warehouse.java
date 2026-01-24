package model;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    private int id;
    private String name;
    private String location;
    private Map<String, Product> products;

    public Warehouse(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.products = new HashMap<>();
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addProduct(Product product, int quantity) {
        String sku = product.getSku();
        if (this.products.containsKey(sku)) {
            this.products.get(sku).addStock(quantity);
        } else {
            product.setQuantity(quantity);
            this.products.put(sku, product);
        }
        System.out.println(quantity + " units of " + product.getName()
         + " (SKU: " + sku + ") added, New Quantity: " + getAvailableQuantity(sku));
    }

    public void removeProduct(Product product, int quantity) {
        String sku =  product.getSku();

        if (this.products.containsKey(sku)) {
            Product current = this.products.get(sku);
            int currentQuantity = current.getQuantity();

            if (currentQuantity >= quantity) {
                current.removeStock(quantity);
                System.out.println(quantity + " units of " + product.getName() + "  (SKU: " + sku + ") removed, Quantity: " + getAvailableQuantity(sku));

                if (getAvailableQuantity(sku) == 0) {
                    products.remove(sku);
                    System.out.println("Product " + sku + " has been removed as quantity is zero.");
                }
                return;
            } else {
                System.out.println("Not sufficient inventory to remove " + quantity + " units of " + product.getName());
                return;
            }
        }

        System.out.println("No product with SKU: " + sku + " found in the inventory.");
    }

    public int getAvailableQuantity(String sku){
        if (this.products.containsKey(sku)) {
            return this.products.get(sku).getQuantity();
        }
        return 0;
    }

    public Product getProductBySku(String sku) {
        return this.products.getOrDefault(sku, null);
    }

    public Product[] getAllProducts() {
        return products.values().toArray(new Product[0]);
    }
}
