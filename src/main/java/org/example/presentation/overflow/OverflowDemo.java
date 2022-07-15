package org.example.presentation.overflow;


import org.example.presentation.util.Util;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class OverflowDemo {

    public static void main(String[] args) {

        Map<String, Function<Flux<PurchaseOrder>, Flux<PurchaseOrder>>> map = Map.of(
                "Kids", OrderProcessor.kidsProcessing(),
                "Automotive", OrderProcessor.automotiveProcessing()
        );

        Set<String> set = map.keySet();

        OrderService.orderStream()
                .filter(p -> set.contains(p.getCategory()))
                .groupBy(PurchaseOrder::getCategory)  // 2 keys
                .flatMap(gf -> map.get(gf.key()).apply(gf)) //flux
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);

    }


}
