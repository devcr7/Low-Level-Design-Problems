package strategy.impl;

import repository.ICacheStorage;
import repository.IDBStorage;
import strategy.IWritePolicy;

public class WriteAroundPolicy<K, V> implements IWritePolicy<K, V> {
    @Override
    public void write(K key, V value, ICacheStorage<K, V> cacheStorage, IDBStorage<K, V> dbStorage) throws Exception {
        System.out.println("Write to db then to cache.");
    }
}
