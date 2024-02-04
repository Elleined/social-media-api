package com.elleined.forumapi;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public interface MultiPartFileDataFactory {

    static MultipartFile empty() {
        return new MockMultipartFile(
                "file",            // Name of the file attribute
                "sample.txt",      // Original file name
                "text/plain",      // Content type
                new byte[0]        // File content as a byte array
        );
    }

    static MultipartFile notEmpty() {
        byte[] fileContent = "This is a sample file content.".getBytes();
        return new MockMultipartFile(
                "file",            // Name of the file attribute
                "sample.txt",      // Original file name
                "text/plain",      // Content type
                fileContent        // File content as a byte array
        );
    }
}