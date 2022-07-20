package org.example.presentation.filereaderflux;

import org.example.presentation.util.Util;

import java.nio.file.Path;
import java.nio.file.Paths;

/*
    Flux example
    Shows reading of file with FileReaderService
*/
public class FileReaderDemo {
    private static final Path EXAMPLE_FILE = Paths.get("src/main/resources/assignment/sec03/file01.txt");
    private static final String ERROR_MESSAGE = "oops";

    public static void main(String[] args) {

        FileReaderService readerService = new FileReaderService();

        readerService.read(EXAMPLE_FILE)
                .map(line -> {
                    Integer integer = Util.faker().random().nextInt(0, 10);
                    if(integer > 8)
                        throw new RuntimeException(ERROR_MESSAGE);
                    return line;
                })
                .take(20)
                .subscribe(Util.subscriber());
    }
}
