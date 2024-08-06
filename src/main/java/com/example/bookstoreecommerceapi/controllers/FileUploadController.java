package com.example.bookstoreecommerceapi.controllers;

import com.example.bookstoreecommerceapi.dto.ResponseObject;
import com.example.bookstoreecommerceapi.services.StorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/api/FileUpload")
@Tag(name = "File Upload")
public class FileUploadController {
    @Autowired
    StorageService storageService;

    @PostMapping
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file")MultipartFile file){
        try {
            String generatedFileName = storageService.storageFile(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ResponseObject(HttpStatus.CREATED, "Thêm file thành công", generatedFileName)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            HttpStatus.BAD_REQUEST,
                            e.getMessage(),
                            null
                    )
            );
        }
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String filename){
        try{
            byte[] bytes = storageService.readFileContent(filename);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        } catch (Exception e){
            return ResponseEntity
                    .noContent().build();
        }
    }
}
