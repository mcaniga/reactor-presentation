package org.example.presentation.hotpublisher;

import org.example.presentation.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

/**
 * Example of hot publisher
 * hot publisher - consumers share the stream
 * cold publisher - consumer has its own stream
 */
public class HotPublisherDemo {
    public static void main(String[] args) {

        Flux<String> movieStream = Flux.fromStream(() -> getMovies())
                .delayElements(Duration.ofSeconds(2))
                .share();

        movieStream
                .subscribe(Util.subscriber("sam")); // sam watch alone the stream

        Util.sleepSeconds(5);

        movieStream
                .subscribe(Util.subscriber("mike")); // mike joins to stream, missed old episodes

        Util.sleepSeconds(60);
    }

    private static Stream<String> getMovies(){
        System.out.println("Got the movie streaming req");
        return Stream.of(
                "Scene 1",
                "Scene 2",
                "Scene 3",
                "Scene 4",
                "Scene 5",
                "Scene 6",
                "Scene 7"
        );
    }
}
