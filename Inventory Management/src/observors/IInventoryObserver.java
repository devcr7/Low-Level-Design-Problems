package observors;

import model.Product;

public interface IInventoryObserver {
    void update(Product product);
}
