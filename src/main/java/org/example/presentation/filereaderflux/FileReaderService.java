package org.example.presentation.filereaderflux;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/*
    Flux example - file reader service that reads file line by line non-blocking way (if subscribeOn was used...)
*/
public class FileReaderService {
    public Flux<String> read(Path path) {
        return Flux.generate(
             openReader(path), // state supplier, will be available as first arg in generator bifunction
             read(),  // generator bifunction - (state, sink)
             closeReader()  // state consumer - cleaning
        );
    }

    private Callable<BufferedReader> openReader(Path path) {
        return () -> Files.newBufferedReader(path);
    }

    // reads file line by line until EOF
    // emits 'complete' signal if no lines remains
    // otherwise emits 'next' signal signal with 'line' as a payload
    private BiFunction<BufferedReader, SynchronousSink<String>, BufferedReader> read() {
        return (bufferedReader, sink) -> {
            try {
                String line = bufferedReader.readLine();
                System.out.println("reading --- " + line);
                if(Objects.isNull(line))
                    sink.complete();
                else
                    sink.next(line);
            } catch (IOException e) {
                sink.error(e);
            }
            return bufferedReader;
        };
    }

    // closes bufferedReader
    private Consumer<BufferedReader> closeReader() {
        return bufferedReader -> {
            try {
                bufferedReader.close();
                System.out.println("--closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
