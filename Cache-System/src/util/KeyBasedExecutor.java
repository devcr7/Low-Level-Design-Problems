package util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class KeyBasedExecutor {
    private final ExecutorService[] executors;
    private final int numExecutors;

    public KeyBasedExecutor(int numExecutors) {
        this.numExecutors = numExecutors;
        this.executors = new ExecutorService[numExecutors];

        for (int i = 0; i < numExecutors; i++) {
            executors[i] = Executors.newSingleThreadExecutor();
        }
    }

    public <T> CompletableFuture<T> submitTask(Object key, Supplier<T> task) {
        int index = getExecutorIndexForKey(key);
        ExecutorService executor = executors[index];
        System.out.println(index + " is the executor index for key " + key);
        return CompletableFuture.supplyAsync(task, executor);
    }

    public int getExecutorIndexForKey(Object key) {
        return Math.abs(key.hashCode() % numExecutors);
    }

    public void shutdown() {
        for (ExecutorService executor: executors) {
            executor.shutdown();
        }
    }
}
