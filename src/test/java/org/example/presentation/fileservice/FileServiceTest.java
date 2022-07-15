package org.example.presentation.fileservice;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileServiceTest {
    private final FileService fileService = new FileService();

    @Test
    void readFile() {
        String expectedContent = "This is file 01.";
        String content = fileService
                .read("file01.txt")
                .block();

        assertThat(content).isEqualTo(expectedContent);
    }
}
