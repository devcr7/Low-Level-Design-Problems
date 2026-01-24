package strategy;

import model.Product;

public interface IReplenishmentStrategy {
    void replenish(Product product);
}
