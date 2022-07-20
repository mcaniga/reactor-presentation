package org.example.presentation.fileservice;

import org.example.presentation.util.Util;
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
public class FileServiceDemo {
    public static void main(String[] args) {
        FileService fileService = new FileService();

        fileService.write("file03.txt", "This is file3")
                .subscribe(Util.onNext(), Util.onError(), Util.onComplete());

        fileService.read("file03.txt")
                .subscribe(Util.onNext(), Util.onError(), Util.onComplete());

        fileService.delete("file03.txt")
                .subscribe(Util.onNext(), Util.onError(), Util.onComplete());

    }
}
