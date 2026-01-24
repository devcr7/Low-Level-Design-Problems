package observors.impl;

import model.Product;
import observors.IInventoryObserver;

public class SupplierNotifier implements IInventoryObserver {
    private final String supplierName;
    private final String supplierEmail;

    public SupplierNotifier(String supplierName, String supplierEmail) {
        this.supplierName = supplierName;
        this.supplierEmail = supplierEmail;
    }

    @Override
    public void update(Product product) {
        if (product.getQuantity() <  product.getThreshold()) {
            System.out.println("Notification sent to " + supplierName + " at email " + supplierEmail
            + " with low stock than threshold for " + product.getName());
        }
    }
}
