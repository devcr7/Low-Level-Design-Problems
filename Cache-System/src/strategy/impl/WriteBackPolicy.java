package strategy.impl;

import repository.ICacheStorage;
import repository.IDBStorage;
import strategy.IWritePolicy;

public class WriteBackPolicy<K, V> implements IWritePolicy<K, V> {
    @Override
    public void write(K key, V value, ICacheStorage<K, V> cacheStorage, IDBStorage<K, V> dbStorage) throws Exception {
        System.out.println("Write to cache then to db.");
    }
}
