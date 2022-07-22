package org.example.presentation.fileservicemono;

import org.example.presentation.util.Util;

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
