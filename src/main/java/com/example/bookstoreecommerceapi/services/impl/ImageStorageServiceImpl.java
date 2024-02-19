package com.example.bookstoreecommerceapi.services.impl;

import com.example.bookstoreecommerceapi.services.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
@Service
public class ImageStorageServiceImpl implements StorageService {
    private final Path storageFolder = Paths.get("uploads");

    public ImageStorageServiceImpl() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException e) {
            throw new RuntimeException("Cannot initial storage", e);
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"png", "jpg", "jpeg", "bnp"})
                .contains(fileExtension.toLowerCase().trim());
    }
    @Override
    public String storageFile(MultipartFile file) {
        try {
            if (file.isEmpty())
                throw new RuntimeException("Failed to storage empty file");
            if (!isImageFile(file))
                throw new RuntimeException("You can only upload image file");
            float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
            //File must be < 5mb
            if (fileSizeInMegabytes > 5.0f)
                throw new RuntimeException("File must be <= 5mb");
            //File must be rename
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generateFileName = UUID.randomUUID().toString().replace("-", "");
            generateFileName = generateFileName + "." + fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(Paths.get(generateFileName))
                    .normalize().toAbsolutePath();

            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                throw new RuntimeException("Cannot storage file outside of current directory");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generateFileName;
        } catch (IOException e) {
            throw new RuntimeException("Fail to storage file", e);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = this.storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists()|| resource.isReadable()){
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }else{
                throw new RuntimeException("Could not read file "+fileName);
            }
        } catch (Exception e){
            throw new RuntimeException("Could not read file "+fileName,e);
        }
    }
}
