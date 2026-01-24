package manager;

import enums.ReplenishmentStrategy;
import factory.ReplenishmentStrategyFactory;
import model.Product;
import model.Warehouse;
import observors.IInventoryObserver;
import strategy.IReplenishmentStrategy;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private static InventoryManager instance;
    private List<Warehouse> warehouses;
    private List<IInventoryObserver> observers;
    private IReplenishmentStrategy replenishmentStrategy;

    private InventoryManager(){
        this.warehouses = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.replenishmentStrategy = ReplenishmentStrategyFactory.getStrategy(ReplenishmentStrategy.JIT);
    }

    public void addObserver(IInventoryObserver observer) {
        this.observers.add(observer);
    }

    private static class InstanceHolder {
        private static final InventoryManager INSTANCE = new InventoryManager();
    }

    public static InventoryManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void setReplenishmentStrategy(ReplenishmentStrategy strategy) {
        this.replenishmentStrategy = ReplenishmentStrategyFactory.getStrategy(strategy);
    }

    public void addWarehouse(Warehouse warehouse){
        this.warehouses.add(warehouse);
    }

    public void removeWarehouse(Warehouse warehouse){
        this.warehouses.remove(warehouse);
    }

    public Product getProductsBySku(String sku){
        for (Warehouse warehouse:  this.warehouses){
            Product product = warehouse.getProductBySku(sku);
            if (product != null){
                return product;
            }
        }
        return null;
    }

    public void checkAndReplenish(String sku){
        Product product = getProductsBySku(sku);
        if (product != null){
            if (product.getQuantity() < product.getThreshold()){
                notifyObservers(product);
                replenishmentStrategy.replenish(product);
            }
        }
    }

    public void performInventoryCheck() {
        for (Warehouse warehouse:  this.warehouses){
            for (Product product: warehouse.getAllProducts()) {
                if (product.getQuantity() < product.getThreshold()){
                    notifyObservers(product);
                    replenishmentStrategy.replenish(product);
                }
            }
        }
    }

    private void notifyObservers(Product product) {
        for (IInventoryObserver observer:observers){
            observer.update(product);
        }
    }
}
