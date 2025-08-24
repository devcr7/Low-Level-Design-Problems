package repository;

public interface IDBStorage<K, V> {
    void write(K key, V value) throws Exception;
    V read(K key) throws Exception;
    void delete(K key) throws Exception;
}
