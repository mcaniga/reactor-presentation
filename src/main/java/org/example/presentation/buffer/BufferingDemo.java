package org.example.presentation.buffer;

import org.example.presentation.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;

import java.time.Duration;

public class BufferingDemo {
    public static void main(String[] args) {
        // subscribeToEventStreamWithoutBuffer();
        // subscribeToEventStreamWithBuffer();
        subscribeToEventStreamWithGrouping();

        Util.sleepSeconds(60);


    }

    private static void subscribeToEventStreamWithoutBuffer() {
        emitEventEachSecond()
                .subscribe(Util.subscriber());
    }

    private static void subscribeToEventStreamWithBuffer() {
        emitEventEachSecond()
                .bufferTimeout(5, Duration.ofSeconds(2))
                .subscribe(Util.subscriber());
    }

    private static void subscribeToEventStreamWithGrouping() {
        emitEventEachSecond()
                .groupBy(event -> Math.abs(event.hashCode() % 2)) // creates flux of two fluxes - one for key 0 and one for key 1
                .subscribe(groupedFlux -> subscribeToGroupedFlux(groupedFlux));
    }

    private static Flux<String> emitEventEachSecond() {
        return Flux
                .interval(Duration.ofMillis(200)) // each second increment value starting with 0, note: interval uses Schedulers.parallel()
                .map(i -> "event" + i)
                .doOnNext(event -> Util.printThreadName("emitting thread: "));
    }

    private static void subscribeToGroupedFlux(GroupedFlux<Integer, String> flux) {
        // print events from associated flux
        flux.subscribe(event -> System.out.println("Key : " + flux.key() + ", Item : " + event));
    }
}
