package com.elleined.socialmediaapi;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

public interface MultipartDataProvider {

    static MockMultipartFile empty() {
        return new MockMultipartFile(
                "file",            // Name of the file attribute
                "sample.txt",      // Original file name
                "text/plain",      // Content type
                new byte[0]        // File content as a byte array
        );
    }

    static MockMultipartFile notEmpty() {
        byte[] fileContent = "This is a sample file content.".getBytes();
        return new MockMultipartFile(
                "file",            // Name of the file attribute
                "sample.txt",      // Original file name
                "text/plain",      // Content type
                fileContent        // File content as a byte array
        );
    }

    static MockMultipartFile getPicture(String name) {
        return new MockMultipartFile(name,
                "sample picture.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "<<jpeg bytes>>".getBytes(StandardCharsets.UTF_8));
    }
}