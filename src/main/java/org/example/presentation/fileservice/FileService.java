package org.example.presentation.fileservice;

import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/*
    Mono example - simple FileService that manipulates with files from resources
    Provides read, write, delete methods manipulating with files in non-blocking way (if subscribeOn was used...)
    File is loaded to memory at one, if file is too big, OOM can occur
*/
public class FileService {
    private static final Path RESOURCES = Paths.get("src/main/resources/");

    public Mono<String> read(String fileName) {
        // Supplier - non args function that returns value
        return Mono.fromSupplier(() -> readFile(fileName));
    }

    public Mono<Void> write(String fileName, String content) {
        // Runnable - non args void function
        return Mono.fromRunnable(() -> writeFile(fileName, content));
    }

    public Mono<Void> delete(String fileName) {
        return Mono.fromRunnable(() -> deleteFile(fileName));
    }

    // Use Mono.just() only for values that you have without computing
    private Mono<String> readWrong(String fileName) {
        // evalutes readFile(fileName) and passes result to Mono.just(), reading is done in current thread with blocking IO
        return Mono.just(readFile(fileName));
    }

    private String readFile(String fileName) {
        try {
            return Files.readString(RESOURCES.resolve(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFile(String fileName, String content) {
        try {
            Files.writeString(RESOURCES.resolve(fileName), content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteFile(String fileName) {
        try {
            Files.delete(RESOURCES.resolve(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
