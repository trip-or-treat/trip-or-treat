package com.triportreat.backend.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String deleteAndUploadFile(MultipartFile file, String existingFileUrl);
    String uploadImage(MultipartFile file);
    void deleteUploadedImage(String url);
    void validateContentType(String contentType);
}
