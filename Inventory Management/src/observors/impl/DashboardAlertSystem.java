package observors.impl;

import model.Product;
import observors.IInventoryObserver;

import java.util.List;

public class DashboardAlertSystem implements IInventoryObserver {
    private final List<String> adminUsers;

    public  DashboardAlertSystem(List<String> adminUsers) {
        this.adminUsers = adminUsers;
    }

    @Override
    public void update(Product product) {
        double stockPercentage = ((double) product.getQuantity() / product.getThreshold()) * 100;
        String stockQuantityMessage = String.format(" %.2f%% percent stock available", stockPercentage);

        if (stockPercentage <= 25) {
            System.out.println("CRITICAL ALERT: " + product.getName() + stockQuantityMessage);
            notifyAdmins(product, "CRITICAL");
        } else if (stockPercentage <= 50) {
            System.out.println("WARNING ALERT: " + product.getName() + stockQuantityMessage);
            notifyAdmins(product, "WARNING");
        }
    }

    private void notifyAdmins(Product product, String level) {
        for (String adminUser : adminUsers) {
            System.out.println("Dashboard notification sent to admin: " +  adminUser + " - " + level
            + " level alert for " + product.getName());
        }
    }
}
