package strategy.impl;

import model.Product;
import strategy.IReplenishmentStrategy;

public class JustInTimeStrategy implements IReplenishmentStrategy {
    @Override
    public void replenish(Product product) {
        product.setQuantity(Math.max(product.getThreshold(), product.getQuantity()));
        System.out.println("Applying JIT replenishment for " + product.getName());
    }
}
