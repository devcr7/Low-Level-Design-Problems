import enums.ProductCategory;
import enums.ReplenishmentStrategy;
import factory.ProductFactory;
import manager.InventoryManager;
import model.Product;
import model.Warehouse;
import observors.impl.DashboardAlertSystem;
import observors.impl.SupplierNotifier;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        InventoryManager inventoryManager = InventoryManager.getInstance();

        Warehouse warehouse1 = new Warehouse(1, "Warehouse 1", "Marathalli");
        Warehouse warehouse2 = new Warehouse(2, "Warehouse 2", "Bellandur");
        inventoryManager.addWarehouse(warehouse1);
        inventoryManager.addWarehouse(warehouse2);

        SupplierNotifier supplierNotifierBellandur = new SupplierNotifier("Bellandur-Supplier", "Bellandur@Suppier.in");
        SupplierNotifier supplierNotifierMarathalli = new SupplierNotifier("Marathalli-Supplier", "Marathalli@Suppier.in");
        inventoryManager.addObserver(supplierNotifierBellandur);
        inventoryManager.addObserver(supplierNotifierMarathalli);

        DashboardAlertSystem alertSystem = new DashboardAlertSystem(List.of("Divyanshu Sir", "Elon Musk"));
        inventoryManager.addObserver(alertSystem);

        Product laptop = ProductFactory.createProduct(
                ProductCategory.ELECTRONICS,
                "SKU1",
                "Laptop",
                75000,
                10
        );

        Product tShirt = ProductFactory.createProduct(
                ProductCategory.CLOTHING,
                "SKU2",
                "T-Shirt",
                5000,
                50
        );

        Product apple = ProductFactory.createProduct(
                ProductCategory.GROCERY,
                "SKU3",
                "Apple",
                20,
                250
        );

        warehouse2.addProduct(laptop, 50);
        warehouse1.addProduct(tShirt, 75);
        warehouse1.addProduct(apple, 150);

        inventoryManager.setReplenishmentStrategy(ReplenishmentStrategy.JIT);

        inventoryManager.performInventoryCheck();

        warehouse2.removeProduct(laptop, 45);

        inventoryManager.setReplenishmentStrategy(ReplenishmentStrategy.JIT);
        inventoryManager.checkAndReplenish("SKU1");
        inventoryManager.performInventoryCheck();

    }
}