package com.example.bookstoreecommerceapi.services;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String storageFile(MultipartFile file);
    byte[] readFileContent(String fileName);
}
