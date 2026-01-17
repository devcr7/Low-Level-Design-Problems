package factory;

import enums.SchedulingStrategy;
import strategy.ISchedulingStrategy;
import strategy.impl.FCFSSchedulingStrategy;
import strategy.impl.LookSchedulingStrategy;
import strategy.impl.ScanSchedulingStrategy;

public class SchedulingStrategyFactory {
    public static ISchedulingStrategy getSchedulingStrategy(SchedulingStrategy schedulingStrategy) {
        return switch (schedulingStrategy) {
            case FCFS -> FCFSSchedulingStrategy.getInstance();
            case LOOK -> LookSchedulingStrategy.getInstance();
            case SCAN -> ScanSchedulingStrategy.getInstance();
        };
    }
}
