package com.elleined.forumapi.service.image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageUploader implements Uploader {
    @Override
    public void upload(String uploadPath, MultipartFile attachment) throws IOException {
        final String uploadPathWithImg = uploadPath + attachment.getOriginalFilename();
        attachment.transferTo(new File(uploadPathWithImg));
        log.debug("Picture uploaded successfully to {}", uploadPathWithImg);
    }
}
