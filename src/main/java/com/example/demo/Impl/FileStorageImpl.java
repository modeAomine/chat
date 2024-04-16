package com.example.demo.Impl;

import com.example.demo.Exception.FileStorageException;
import com.example.demo.Service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageImpl implements FileStorageService {
    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public String storeFile(MultipartFile file) {
        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String fullFilePath = uploadPath + "/" + filename;

            Path targetLocation = Paths.get(fullFilePath);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return filename;
        } catch (IOException ex) {
            throw new FileStorageException("Не удалось сохранить файл " + file.getOriginalFilename(), ex);
        }
    }

    @Override
    public String saveFile(MultipartFile file, String uploadPath) throws IOException {
        String newFilename = UUID.randomUUID() + "." + file.getOriginalFilename();
        String fullFilePath = uploadPath + "/" + newFilename;
        File newFile = new File(fullFilePath);
        file.transferTo(newFile);
        return newFilename;
    }

    @Override
    public void updateFile(String oldFileName, MultipartFile newFile, String uploadPath) throws IOException {
        if (newFile != null && !newFile.isEmpty()) {
            String newFilename = saveFile(newFile, uploadPath);
            deleteFile(oldFileName);
        }
    }

    @Override
    public void deleteFile(String fileName) {
        File fileToDelete = new File(uploadPath + "/" + fileName);
        if (fileToDelete.exists() && fileToDelete.isFile()) {
            if (!fileToDelete.delete()) {
                throw new IllegalArgumentException("Ошибка удаления файла");
            }
        }
    }
}