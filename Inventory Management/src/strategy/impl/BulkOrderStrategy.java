package strategy.impl;

import model.Product;
import strategy.IReplenishmentStrategy;

public class BulkOrderStrategy implements IReplenishmentStrategy {
    @Override
    public void replenish(Product product) {
        product.setQuantity(Math.max(product.getThreshold(), product.getQuantity()));
        System.out.println("Applying Bulk Order replenishment for " + product.getName());
    }
}
