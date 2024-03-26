package com.example.demo.Service;

import com.example.demo.Model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    String storeFile(MultipartFile file);

    String saveFile(MultipartFile file, String uploadPath) throws IOException;

    void updateFile(String oldFileName, MultipartFile newFile, String uploadPath) throws IOException;

    void deleteFile(String fileName);
}
