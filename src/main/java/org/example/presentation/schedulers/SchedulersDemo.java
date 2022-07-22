package org.example.presentation.schedulers;

import org.example.presentation.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import static org.example.presentation.util.Util.printThreadName;

public class SchedulersDemo {
    public static void main(String[] args) {
        // choose scheduler:
        //Flux<Integer> flux = emitOneWithDefaultSchedulers();
        Flux<Integer> flux = emitOneWithBoundedELasticSchedulers();

        // choose subscribers, boundedElastic = one reactor thread per each consumer:
        // makeOneSubscriber(flux);
        makeTwoSubscribers(flux);

        Util.sleepSeconds(5);

    }

    private static void makeOneSubscriber(Flux<Integer> flux) {
        flux.subscribe(v -> printThreadName("subscriber " + v));
    }

    private static void makeTwoSubscribers(Flux<Integer> flux) {
        flux.subscribe(v -> printThreadName("subscriber 1 :" + v));
        flux.subscribe(v -> printThreadName("subscriber 2 :" + v));
    }

    // does all the work with current thread
    private static Flux<Integer> emitOneWithDefaultSchedulers() {
        return Flux.range(1, 10)
                .doOnNext(i -> printThreadName("next " + i)) // current thread
                .doFirst(() -> printThreadName("first2")) // current thread
                .doFirst(() -> printThreadName("first1")); // current thread
    }

    private static Flux<Integer> emitOneWithBoundedELasticSchedulers() {
        return Flux.range(1, 10)
                .doOnNext(i -> printThreadName("next " + i)) // done by reactor thread
                .doFirst(() -> printThreadName("first2")) // done by reactor thread
                .subscribeOn(Schedulers.boundedElastic()) // switch subscribers from current thread to thread from reactor pool
                .doFirst(() -> printThreadName("first1")); // done by current thread
    }
}
