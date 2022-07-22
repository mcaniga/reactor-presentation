package org.example.presentation.fileservicemono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Examples of unit testing with Step Verifier
 */
class StepVerifierTest {
    @Test
    void fluxEmitsSpecifiedElements() {
        Flux<Integer> just = Flux.just(1, 2, 3);

        StepVerifier.create(just)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .verifyComplete();
    }

    @Test
    void fluxEmitsErrorMessageAfterThreeElements() {
        String errorMessage = "oops";

        Flux<Integer> just = Flux.just(1, 2, 3);
        Flux<Integer> error = Flux.error(new RuntimeException(errorMessage));
        Flux<Integer> concat = Flux.concat(just, error);

        StepVerifier.create(concat)
                .expectNext(1, 2, 3)
                .verifyErrorMessage(errorMessage);
    }
}
