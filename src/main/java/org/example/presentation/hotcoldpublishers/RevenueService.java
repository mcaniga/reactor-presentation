package org.example.presentation.hotcoldpublishers;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RevenueService {
    private final Map<String, Double> revenueDB = new HashMap<>();

    public RevenueService(){
        revenueDB.put("Kids", 0.0); // (category, revenue)
        revenueDB.put("Automotive", 0.0);
    }

    public Consumer<PurchaseOrder> increaseCategoryRevenue() {
        return purchaseOrder -> revenueDB.computeIfPresent(
                purchaseOrder.getCategory(), (category, revenue) -> revenue + purchaseOrder.getPrice()
        );
    }

    public Flux<String> streamRevenueDB() {
        return Flux.interval(Duration.ofSeconds(2))
                    .map(i -> revenueDB.toString());
    }

}
