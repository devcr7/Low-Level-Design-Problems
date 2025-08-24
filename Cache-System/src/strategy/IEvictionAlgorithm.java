package strategy;

public interface IEvictionAlgorithm<K> {
    void keyAccessed(K key) throws Exception;
    K evictKey() throws Exception;
}
