package repository.impl;

import repository.IDBStorage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleDBStorage<K, V> implements IDBStorage<K, V> {
    private final Map<K, V> database;

    public SimpleDBStorage() {
        database = new ConcurrentHashMap<>();
    }

    @Override
    public void write(K key, V value) throws Exception {
        database.put(key, value);
    }

    @Override
    public V read(K key) throws Exception {
        if (!database.containsKey(key)) {
            throw new Exception("Key not in database: " + key);
        }
        return database.get(key);
    }

    @Override
    public void delete(K key) throws Exception {
        if (!database.containsKey(key)) {
            throw new Exception("Key not in database: " + key);
        }
        database.remove(key);
    }
}
