package factory;

import enums.ReplenishmentStrategy;
import strategy.IReplenishmentStrategy;
import strategy.impl.BulkOrderStrategy;
import strategy.impl.JustInTimeStrategy;

public class ReplenishmentStrategyFactory {
    private static final JustInTimeStrategy justInTimeStrategy = new JustInTimeStrategy();
    private static final BulkOrderStrategy bulkOrderStrategy = new BulkOrderStrategy();

    public static IReplenishmentStrategy getStrategy(ReplenishmentStrategy strategy) {
        return switch (strategy) {
            case JIT -> justInTimeStrategy; // JustInTimeStrategy.getInstance();
            case BULK_ORDER ->  bulkOrderStrategy;
        };
    }
}
