package factory;

import enums.ProductCategory;
import model.ClothingProduct;
import model.ElectronicsProduct;
import model.GroceryProduct;
import model.Product;

public class ProductFactory {
    public static Product createProduct(ProductCategory category, String sku, String name, double price, int threshold) {
        return switch (category) {
            case ELECTRONICS ->  new ElectronicsProduct(sku, name, price, threshold, category);
            case CLOTHING ->   new ClothingProduct(sku, name, price, threshold, category);
            case GROCERY ->   new GroceryProduct(sku, name, price, threshold, category);
            default -> throw new IllegalArgumentException("Invalid product category: " +  category);
        };
    }
}
