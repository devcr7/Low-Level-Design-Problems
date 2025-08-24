package strategy;

import repository.ICacheStorage;
import repository.IDBStorage;

public interface IWritePolicy<K, V> {
    void write(K key, V value, ICacheStorage<K, V> cacheStorage, IDBStorage<K, V> dbStorage) throws Exception;
}
