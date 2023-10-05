package com.elleined.forumapi.service.image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface Uploader {
    void upload(String uploadPath, MultipartFile attachment) throws IOException;
}
