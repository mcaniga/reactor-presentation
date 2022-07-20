package org.example.presentation.hotcoldpublishers;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Contains in memory inventory DB represented by HashMap
 */
public class InventoryService {
    private final Map<String, Integer> inventoryDB = new HashMap<>();

    public InventoryService() {
        initDB();
    }

    private void initDB() {
        inventoryDB.put("Kids", 100); // (category, count)
        inventoryDB.put("Automotive", 100);
    }

    // updates the count in inventoryDB if category key is present
    public Consumer<PurchaseOrder> decreaseCategoryCount() {
        return purchaseOrder -> inventoryDB.computeIfPresent(
                purchaseOrder.getCategory(), (category, count) -> count - purchaseOrder.getQuantity()
        );
    }

    public Flux<String> streamInventoryDB() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(i -> inventoryDB.toString());
    }
}
