package strategy.impl;

import repository.ICacheStorage;
import repository.IDBStorage;
import strategy.IWritePolicy;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WriteThroughPolicy<K, V> implements IWritePolicy<K, V> {
    private final ExecutorService executor;

    public WriteThroughPolicy() {
        executor = Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public void write(K key, V value, ICacheStorage<K, V> cacheStorage, IDBStorage<K, V> dbStorage) throws Exception {
        CompletableFuture<Void> cacheFuture = CompletableFuture.runAsync(() -> {
           try {
               cacheStorage.put(key, value);
           } catch (Exception e) {
               throw new CompletionException(e);
           }
        }, executor);

        CompletableFuture<Void> dbFuture = CompletableFuture.runAsync(() ->{
            try {
                dbStorage.write(key, value);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }, executor);
    }
}
