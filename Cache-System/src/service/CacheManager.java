package service;

import repository.ICacheStorage;
import repository.IDBStorage;
import strategy.IEvictionAlgorithm;
import strategy.IWritePolicy;
import util.KeyBasedExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/*
    We can leverage request collapsing (prevent same key concurrent fetches from DB), hot loading (cache warming/ preload), event logging, and head of line blocking.
    Read before refresh -> on demand caching
 */

public class CacheManager<K, V> {
    private final ICacheStorage<K, V> cacheStorage;
    private final IDBStorage<K, V> dbStorage;
    private final IWritePolicy<K, V> writePolicy;
    private final IEvictionAlgorithm<K> evictionAlgorithm;
    private final KeyBasedExecutor keyBasedExecutor;

    public CacheManager(ICacheStorage<K, V> cacheStorage, IDBStorage<K, V> dbStorage, IWritePolicy<K, V> writePolicy, IEvictionAlgorithm<K> evictionAlgorithm, int numExecutors) {
        this.cacheStorage = cacheStorage;
        this.dbStorage = dbStorage;
        this.writePolicy = writePolicy;
        this.evictionAlgorithm = evictionAlgorithm;
        this.keyBasedExecutor = new KeyBasedExecutor(numExecutors);
    }

    public CompletableFuture<V> accessData(K key) {
        return keyBasedExecutor.submitTask(key, () -> {
            try {
                if (!cacheStorage.containsKey(key)) {
                    throw new Exception("Key not found in cache:" + key);
                }
                evictionAlgorithm.keyAccessed(key);
                return cacheStorage.get(key);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    public CompletableFuture<Void> updateData(K key, V value) {
        return keyBasedExecutor.submitTask(key, () -> {
            try {
                if (cacheStorage.containsKey(key)) {
                    writePolicy.write(key, value, cacheStorage, dbStorage);
                    evictionAlgorithm.keyAccessed(key);
                } else {
                    if (cacheStorage.size() >= cacheStorage.getCapacity()) {
                        K evictedKey = evictionAlgorithm.evictKey();

                        if (evictedKey != null) {
                            int currentIdx = keyBasedExecutor.getExecutorIndexForKey(key);
                            int evictedIdx = keyBasedExecutor.getExecutorIndexForKey(evictedKey);

                            if(currentIdx == evictedIdx) {
                                // The parent CompletableFuture is already executing on the thread mapped to this key.
                                // If the evicted key is assigned to the same thread as the current key, submitting the removal
                                // task back to the same executor would be redundant and could even cause self-deadlock.
                                // Hence, we directly remove the entry from cacheStorage in this case.
                                cacheStorage.remove(evictedKey);
                            } else {
                                CompletableFuture<Void> removalFuture = keyBasedExecutor.submitTask(evictedKey, () -> {
                                    try {
                                        cacheStorage.remove(evictedKey);
                                        return null;
                                    } catch (Exception e) {
                                        throw new CompletionException(e);
                                    }
                                });
                                removalFuture.join();
                            }
                        }
                    }
                    writePolicy.write(key, value, cacheStorage, dbStorage);
                    evictionAlgorithm.keyAccessed(key);
                }
                return null;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    public void shutdown() {
        keyBasedExecutor.shutdown();
    }
}
