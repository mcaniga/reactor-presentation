package org.example.presentation.filereaderflux;

import org.example.presentation.util.Util;

import java.nio.file.Path;
import java.nio.file.Paths;

/*
    Flux example
    Shows reading of file with FileReaderService
*/
public class FileReaderDemo {
    private static final Path EXAMPLE_FILE = Paths.get("src/main/resources/file02.txt");

    public static void main(String[] args) {

        FileReaderService readerService = new FileReaderService();

        readerService.read(EXAMPLE_FILE)
                .take(20)
                .subscribe(Util.subscriber());
    }
}
