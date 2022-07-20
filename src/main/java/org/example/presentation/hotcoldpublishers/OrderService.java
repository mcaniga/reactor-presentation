package org.example.presentation.hotcoldpublishers;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Objects;

public class OrderService {
    private Flux<PurchaseOrder> purchaseOrderFlux;

    // if flux is not initialized, creates new order stream, otherwise use initialized one
    public Flux<PurchaseOrder> orderStream() {
        if (Objects.isNull(purchaseOrderFlux)) {
            initializePurchaseOrderFlux();
        }
        return purchaseOrderFlux;
    }

    private void initializePurchaseOrderFlux() {
        purchaseOrderFlux = generatePurchaseOrderEach100Ms();
    }

    private Flux<PurchaseOrder> generatePurchaseOrderEach100Ms() {
        return Flux.interval(Duration.ofMillis(100)) // note: .interval() uses Schedulers.parallel
                .map(i -> new PurchaseOrder())
                .publish()
                .refCount(2);
    }
}
