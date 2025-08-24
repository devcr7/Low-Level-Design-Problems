import repository.ICacheStorage;
import repository.IDBStorage;
import repository.impl.InMemoryCacheStorage;
import repository.impl.SimpleDBStorage;
import service.CacheManager;
import strategy.IEvictionAlgorithm;
import strategy.IWritePolicy;
import strategy.impl.LRUEvictionAlgorithm;
import strategy.impl.WriteThroughPolicy;

public class Main {
    public static void main(String[] args) {
        try {
            ICacheStorage<String, String> cacheStorage = new InMemoryCacheStorage<>(5);
            IDBStorage<String, String> dbStorage = new SimpleDBStorage<>();

            IWritePolicy<String, String> writePolicy = new WriteThroughPolicy<>();
            IEvictionAlgorithm<String> evictionAlgorithm = new LRUEvictionAlgorithm<>();

            CacheManager<String, String> cacheManager = new CacheManager<>(cacheStorage, dbStorage, writePolicy, evictionAlgorithm, 4);

            cacheManager.updateData("A", "Apple").join();
            cacheManager.updateData("B", "Banana").join();
            cacheManager.updateData("C", "Cherry").join();
            cacheManager.updateData("D", "Durian").join();
            cacheManager.updateData("E", "Elderberry").join();

            // Capacity reached hence A will be evicted as per LRU
            cacheManager.updateData("F", "Fig").join();

            try {
                String valueA = cacheManager.accessData("A").join();
                System.out.println("A: " + valueA);
            } catch (Exception e) {
                System.out.println("A is evicted or not found in cache.");
            }

            String valueF = cacheManager.accessData("F").join();
            System.out.println("F: " + valueF);

            // demonstrating "read your own writes via thread affinity"
            cacheManager.updateData("B", "Blueberry").join();
            String valueB = cacheManager.accessData("B").join();
            System.out.println("B: " + valueB);

            cacheManager.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}