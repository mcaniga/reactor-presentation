package org.example.presentation.hooks;

import org.example.presentation.util.Util;
import reactor.core.publisher.Flux;

/**
 * Demo on hooks (callbacks)
 * Hooks enables to react on specific lifecycle event
 */
public class HooksDemo {
    public static void main(String[] args) {

        Flux.create(fluxSink -> {
                    System.out.println("inside create");
                    for (int i = 0; i < 5; i++) {
                        fluxSink.next(i);
                    }
                    //fluxSink.complete();
                    fluxSink.error(new RuntimeException("oops"));
                    System.out.println("--completed");
                })
                .doOnComplete(() -> System.out.println("doOnComplete"))
                .doFirst(() -> System.out.println("doFirst")) // 1
                .doOnNext(o -> System.out.println("doOnNext : " + o))
                .doOnSubscribe(s -> System.out.println("doOnSubscribe" + s)) // 2
                .doOnRequest(l -> System.out.println("doOnRequest : " + l))  // 3
                .doOnError(err -> System.out.println("doOnError : " + err.getMessage()))
                .doOnTerminate(() -> System.out.println("doOnTerminate"))
                .doOnCancel(() -> System.out.println("doOnCancel"))
                .doFinally(signal -> System.out.println("doFinally 1 : " + signal))
                .doOnDiscard(Object.class, o -> System.out.println("doOnDiscard : " + o))
                .take(2) // takes 0, 1 and discards 2, 3, 4
                .doFinally(signal -> System.out.println("doFinally 2 : " + signal))
                .subscribe(Util.subscriber());


    }
}
